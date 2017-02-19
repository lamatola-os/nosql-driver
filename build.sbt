name := "NoSQL-Driver"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.93"

libraryDependencies += "org.json" % "json" % "20160810"

libraryDependencies += "com.specs" % "specs-lib" % "1.0-SNAPSHOT"

resolvers += Resolver.mavenLocal
