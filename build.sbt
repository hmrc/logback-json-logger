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

import sbt.Keys._
import sbt._
import uk.gov.hmrc.versioning.SbtGitVersioning

val libName = "logback-json-logger"

lazy val root = Project(libName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
  .settings(
    majorVersion                     := 4,
    makePublicallyAvailableOnBintray := true
  )
  .settings(
    scalaVersion        := "2.11.12",
    libraryDependencies ++= Seq(
      "org.apache.commons"         % "commons-lang3"    % "3.4",
      "com.fasterxml.jackson.core" % "jackson-core"     % "2.7.1",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.7.1",
      "commons-io"                 % "commons-io"       % "2.4",
      "ch.qos.logback"             % "logback-core"     % "1.2.3",
      "ch.qos.logback"             % "logback-classic"  % "1.2.3",
      "com.typesafe"               % "config"           % "1.3.1",
      "org.scalatest"              %% "scalatest"       % "3.0.5"  % Test,
      "org.pegdown"                % "pegdown"          % "1.6.0"  % Test,
      "org.mockito"                % "mockito-core"     % "2.21.0" % Test,
      "com.typesafe.play"          %% "play-json"       % "2.6.10" % Test
    ),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases")
    )
  )
