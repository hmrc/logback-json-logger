resolvers ++= Seq(
  Resolver.bintrayIvyRepo("hmrc", "sbt-plugin-releases"),
  Resolver.typesafeRepo("releases")
)
resolvers += "HMRC Releases" at "https://dl.bintray.com/hmrc/releases"

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "1.13.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-git-versioning" % "1.15.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-artifactory" % "0.17.0")
