/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.logging

import java.io.{PrintWriter, StringWriter}
import java.net.InetAddress

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.{ILoggingEvent, ThrowableProxy}
import ch.qos.logback.core.ContextBase
import org.apache.commons.lang3.time.FastDateFormat
import org.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

import scala.collection.JavaConverters._

class JsonEncoderSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "Json-encoded message" should {
    "contain all required fields" in {

      val jsonEncoder = new JsonEncoder()
      val event       = mock[ILoggingEvent]

      when(event.getTimeStamp).thenReturn(1)
      when(event.getLevel).thenReturn(Level.INFO)
      when(event.getThreadName).thenReturn("my-thread")
      when(event.getFormattedMessage).thenReturn("my-message")
      when(event.getLoggerName).thenReturn("logger-name")
      when(event.getMDCPropertyMap).thenReturn(Map("myMdcProperty" -> "myMdcValue").asJava)

      val testException = new Exception("test-exception")
      val stringWriter  = new StringWriter()
      testException.printStackTrace(new PrintWriter(stringWriter))
      when(event.getThrowableProxy).thenReturn(new ThrowableProxy(testException))

      jsonEncoder.setContext {
        val ctx = new ContextBase()
        ctx.putProperty("myKey", "myValue")
        ctx
      }

      val result       = new String(jsonEncoder.encode(event), "UTF-8")
      val resultAsJson = Json.parse(result)

      (resultAsJson \ "app"          ).as[String] shouldBe "my-app-name"
      (resultAsJson \ "hostname"     ).as[String] shouldBe InetAddress.getLocalHost.getHostName
      (resultAsJson \ "timestamp"    ).as[String] shouldBe FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSSZZ").format(1)
      (resultAsJson \ "message"      ).as[String] shouldBe "my-message"
      (resultAsJson \ "exception"    ).as[String] should include("test-exception")
      (resultAsJson \ "exception"    ).as[String] should include("java.lang.Exception")
      (resultAsJson \ "exception"    ).as[String] should include(stringWriter.toString)
      (resultAsJson \ "logger"       ).as[String] shouldBe "logger-name"
      (resultAsJson \ "thread"       ).as[String] shouldBe "my-thread"
      (resultAsJson \ "level"        ).as[String] shouldBe "INFO"
      (resultAsJson \ "mykey"        ).as[String] shouldBe "myValue"
      (resultAsJson \ "mymdcproperty").as[String] shouldBe "myMdcValue"
    }
  }
}
