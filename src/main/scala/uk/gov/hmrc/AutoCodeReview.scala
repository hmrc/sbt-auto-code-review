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
package uk.gov.hmrc

import sbt.Keys._
import sbt._
import wartremover._

object AutoCodeReview extends sbt.AutoPlugin {

  override def requires = WartRemover

  override def trigger = allRequirements

//  val logger = new ConsoleLogger

  def makeExcludedFiles(rootDir:File):Seq[String] = {
    val excluded = findPlayConfFiles(rootDir) ++ findSbtFiles(rootDir)

    println(s"[auto-code-review] excluding the following files: ${excluded.mkString(",")}")
    excluded
  }


  def findSbtFiles(rootDir: File): Seq[String] = {
    if(rootDir.getName == "project") {
      rootDir.listFiles().map(_.getName).toSeq
    } else {
      Seq()
    }
  }

  def findPlayConfFiles(rootDir: File): Seq[String] = {
    Option { new File(rootDir, "conf").listFiles() }.fold(Seq[String]()) { confFiles =>
      confFiles.map(_.getName.replace(".routes", ".Routes"))
    }
  }

  override def projectSettings = Seq(
    wartremoverErrors ++= Seq(),
    wartremoverWarnings ++= Warts.allBut(
      Wart.NoNeedForMonad,
      Wart.Nothing,
      Wart.Any,
      Wart.NonUnitStatements,
      Wart.DefaultArguments,
      Wart.Product
    ),
    wartremoverExcluded ++= makeExcludedFiles(baseDirectory.value) :+ "controllers.ref"
  )
}
