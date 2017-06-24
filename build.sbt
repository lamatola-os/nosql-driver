name := "NoSQL-Driver"

version := "1.0"

scalaVersion := "2.11.8"

//ivyScala := ivyScala.value.map {_.copy(overrideScalaVersion = true)}

libraryDependencies += "org.json" % "json" % "20160810"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.93"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "com.specs" % "specs-lib" % "1.0" excludeAll( ExclusionRule(organization = "com.typesafe.akka") )

resolvers += Resolver.mavenLocal
