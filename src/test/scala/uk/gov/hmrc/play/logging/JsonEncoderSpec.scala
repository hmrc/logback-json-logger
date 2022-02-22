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
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.{JsLookupResult, Json}

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

      (resultAsJson \ "app").asString           shouldBe "my-app-name"
      (resultAsJson \ "hostname").asString      shouldBe InetAddress.getLocalHost.getHostName
      (resultAsJson \ "timestamp").asString     shouldBe FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSSZZ").format(1)
      (resultAsJson \ "message").asString       shouldBe "my-message"
      (resultAsJson \ "exception").asString     should include("test-exception")
      (resultAsJson \ "exception").asString     should include("java.lang.Exception")
      (resultAsJson \ "exception").asString     should include(stringWriter.toString)
      (resultAsJson \ "logger").asString        shouldBe "logger-name"
      (resultAsJson \ "thread").asString        shouldBe "my-thread"
      (resultAsJson \ "level").asString         shouldBe "INFO"
      (resultAsJson \ "mykey").asString         shouldBe "myValue"
      (resultAsJson \ "mymdcproperty").asString shouldBe "myMdcValue"

    }
  }

  implicit class JsLookupResultOps(jsLookupResult: JsLookupResult) {
    def asString: String = jsLookupResult.get.as[String]
  }

}
