logback-json-logger
================

[![Build Status](https://travis-ci.org/hmrc/logback-json-logger.svg)](https://travis-ci.org/hmrc/logback-json-logger) [ ![Download](https://api.bintray.com/packages/hmrc/releases/logback-json-logger/images/download.svg) ](https://bintray.com/hmrc/releases/logback-json-logger/_latestVersion)

This is a JSON encoder for [Logback](http://logback.qos.ch/documentation.html) applications logger. It uses Mapped Diagnostic Contexts ([MDC](http://logback.qos.ch/manual/mdc.html)) to include appropriate context information in each log message too.

These are the properties (for your `application.conf`) that you can use to alter the log output:

```
appName = MyApplication
logger.json.dateformat = yyyy-MM-dd HH:mm:ss.SSSZZ
```

If you do not set the date format, it defaults to `yyyy-MM-dd HH:mm:ss.SSSZZ`.

You can then use the encoder in your logger configuration.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="uk.gov.hmrc.play.logging.JsonEncoder"/>
    </appender>

    <logger name="uk.gov" level="DEBUG"/>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```


## Adding to your service

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "logback-json-logger" % "[INSERT_VERSION]"
```

Use version >= 2.0.0 for Java 8

Use version >= 4.0.0 for Play 2.5.14 or later

## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

