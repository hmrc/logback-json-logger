play-json-logger
================

This is a Json encoder for Play applications logger. It uses MDC to output all its properties in the log message too.

These are the properties (for your `application.conf`) that you can use to alter the log output:

```
appName = MyApplication
logger.json.dateformat = yyyy-MM-dd HH:mm:ss.SSSZZ
```

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

libraryDependencies += "uk.gov.hmrc" %% "play-json-logger" % "1.0.0"
```

## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
