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
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtBuildInfo

object PluginBuild extends Build {

  val pluginName = "sbt-auto-code-review"
  val pluginVersion = "0.2.0-SNAPSHOT"

  lazy val orderIdEncoder = Project(pluginName, file("."))
    .settings(scalaSettings: _*)
    .settings(
      version := pluginVersion,
      sbtPlugin := true,
      targetJvm := "jvm-1.7",
      scalaVersion := "2.10.4",
      organization := "uk.gov.hmrc",
      crossScalaVersions := Seq("2.11.5"),
      addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.11" cross CrossVersion.full)
    )
    .settings(SbtBuildInfo(): _*)
    .settings(BintraySettings(Some("HMRC")): _*)
    .settings(BuildDescriptionSettings(): _*)
}

object BintraySettings {

  import bintray.Plugin._
  import bintray.Keys._

  def apply(org : Option[String]) = bintrayPublishSettings ++ Seq (
    publishMavenStyle := false,
    repository in bintray := "releases",
    bintrayOrganization in bintray := org,
    licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
  )
}

object BuildDescriptionSettings {

  def apply() = Seq(
    pomExtra := (<url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <connection>scm:git@github.com:hmrc/sbt-auto-code-review.git</connection>
        <developerConnection>scm:git@github.com:hmrc/sbt-auto-code-review.git</developerConnection>
        <url>git@github.com:hmrc/sbt-auto-code-review.git</url>
      </scm>
      <developers>
        <developer>
          <id>charleskubicek</id>
          <name>Charles Kubicek</name>
          <url>http://www.equalexperts.com</url>
        </developer>
        <developer>
          <id>duncancrawford</id>
          <name>Duncan Crawford</name>
          <url>http://www.equalexperts.com</url>
        </developer>
      </developers>)
  )
}
