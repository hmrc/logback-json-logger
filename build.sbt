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
import uk.gov.hmrc.versioning.SbtGitVersioning

val libName = "logback-json-logger"

lazy val root = Project(libName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    majorVersion := 4,
    makePublicallyAvailableOnBintray := true
  )
  .settings(
    scalaVersion := "2.12.10",
    crossScalaVersions := Seq("2.11.12", "2.12.10"),
    libraryDependencies ++= Seq(
      "org.apache.commons" % "commons-lang3" % "3.9",
      // force dependencies due to security flaws found in jackson-databind < 2.9.x using XRay
      "com.fasterxml.jackson.core"     % "jackson-core"            % "2.10.3",
      "com.fasterxml.jackson.core"     % "jackson-databind"        % "2.10.3",
      "com.fasterxml.jackson.core"     % "jackson-annotations"     % "2.10.3",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8"   % "2.10.3",
      "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.10.3",
      "commons-io"                     % "commons-io"              % "2.6",
      "ch.qos.logback"                 % "logback-core"            % "1.2.3",
      "ch.qos.logback"                 % "logback-classic"         % "1.2.3",
      "com.typesafe"                   % "config"                  % "1.4.0",
      "com.vladsch.flexmark"           % "flexmark-all"            % "0.35.10"  % Test,
      "org.scalatest"                  %% "scalatest"              % "3.1.1"    % Test,
      "org.scalatestplus"              %% "scalatestplus-mockito"  % "1.0.0-M2" % Test,
      "org.mockito"                    % "mockito-core"            % "3.3.3"    % Test,
      "com.typesafe.play"              %% "play-json"              % "2.6.14"   % Test
    ),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases")
    )
  )
