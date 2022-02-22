/*
 * Copyright 2018 HM Revenue & Customs
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

import sbt.Keys._
import sbt._

lazy val root = Project("logback-json-logger", file("."))
  .settings(
    majorVersion := 5,
    isPublicArtefact := true,
    scalaVersion := "2.12.15",
    crossScalaVersions := Seq("2.12.15", "2.13.7"),
    libraryDependencies ++= Seq(
      "org.apache.commons"             %  "commons-lang3"           % "3.11",
      "com.fasterxml.jackson.core"     %  "jackson-core"            % "2.10.3",
      "com.fasterxml.jackson.core"     %  "jackson-databind"        % "2.10.3",
      "ch.qos.logback"                 %  "logback-core"            % "1.2.3",
      "ch.qos.logback"                 %  "logback-classic"         % "1.2.3",
      "com.typesafe"                   %  "config"                  % "1.4.1",
      "com.vladsch.flexmark"           %  "flexmark-all"            % "0.35.10"   % Test,
      "org.scalatest"                  %% "scalatest"               % "3.2.3"     % Test,
      "org.scalatestplus"              %% "scalatestplus-mockito"   % "1.0.0-M2"  % Test,
      "org.mockito"                    %  "mockito-core"            % "3.7.7"     % Test,
      "com.typesafe.play"              %% "play-json"               % "2.9.2"     % Test
    )
  )
