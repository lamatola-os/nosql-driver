name := "NoSQL-Driver"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.json" % "json" % "20160810"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.93"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "com.specs" % "specs-lib" % "1.0-SNAPSHOT"

resolvers += Resolver.mavenLocal
