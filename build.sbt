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
    majorVersion        := 5,
    isPublicArtefact    := true,
    scalaVersion        := "2.13.12",
    crossScalaVersions  := Seq("2.12.18", "2.13.12", "3.3.3"),
    libraryDependencies ++= Seq(
      "org.apache.commons"             %  "commons-lang3"           % "3.14.0",
      // jackson and logback will be evicted depending on the version of Play
      "com.fasterxml.jackson.core"     %  "jackson-core"            % "2.11.4",
      "com.fasterxml.jackson.core"     %  "jackson-databind"        % "2.11.4",
      "ch.qos.logback"                 %  "logback-core"            % "1.2.12",
      "ch.qos.logback"                 %  "logback-classic"         % "1.2.12",
      "com.typesafe"                   %  "config"                  % "1.4.2",
      "com.vladsch.flexmark"           %  "flexmark-all"            % "0.64.8"    % Test,
      "org.scalatest"                  %% "scalatest"               % "3.2.13"    % Test,
      "org.scalatestplus"              %% "mockito-3-4"             % "3.2.10.0"  % Test,
      "org.playframework"              %% "play-json"               % "3.0.2"     % Test
    )
  )
