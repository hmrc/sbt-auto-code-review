addSbtPlugin("uk.gov.hmrc" % "sbt-utils" % "2.1.0")

resolvers += Resolver.url(
"bintray-sbt-plugin-releases",
    url("https://dl.bintray.com/content/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.2.0")