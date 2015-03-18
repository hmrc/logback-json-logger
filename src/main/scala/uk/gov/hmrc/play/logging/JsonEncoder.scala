/*
 * Copyright 2015 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.logging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.JsonGenerator.Feature
import ch.qos.logback.core.encoder.EncoderBase
import ch.qos.logback.classic.spi.{ ThrowableProxyUtil, ILoggingEvent }
import java.net.InetAddress
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.commons.io.IOUtils._
import play.api.Play

class JsonEncoder extends EncoderBase[ILoggingEvent] {

  import scala.collection.JavaConversions._

  private val mapper = new ObjectMapper().configure(Feature.ESCAPE_NON_ASCII, true)

  lazy val appName = Play.current.configuration.getString("appName").getOrElse("APP NAME NOT SET")

  private lazy val dateFormat = FastDateFormat.getInstance(
    Play.current.configuration.getString("logger.json.dateformat").getOrElse("yyyy-MM-dd HH:mm:ss.SSSZZ")
  )

  override def doEncode(event: ILoggingEvent) {
    val eventNode = mapper.createObjectNode

    eventNode.put("app", appName)
    eventNode.put("hostname", InetAddress.getLocalHost.getHostName)
    eventNode.put("timestamp", dateFormat.format(event.getTimeStamp))
    eventNode.put("message", event.getFormattedMessage)

    Option(event.getThrowableProxy).map(p =>
      eventNode.put("exception", ThrowableProxyUtil.asString(p))
    )

    eventNode.put("logger", event.getLoggerName)
    eventNode.put("thread", event.getThreadName)
    eventNode.put("level", event.getLevel.toString)

    Option(getContext).map(c =>
      c.getCopyOfPropertyMap.toMap foreach { case (k, v) => eventNode.put(k.toLowerCase, v) }
    )
    event.getMDCPropertyMap.toMap foreach { case (k, v) => eventNode.put(k.toLowerCase, v) }

    write(mapper.writeValueAsBytes(eventNode), outputStream)
    write(LINE_SEPARATOR, outputStream)

    outputStream.flush()
  }

  override def close() {
    write(LINE_SEPARATOR, outputStream)
  }
}
