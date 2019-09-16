name := "xlsxloader"
version := "1.0"

resolvers += Resolver.sonatypeRepo("snapshots")
scalaVersion := "2.13.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4"
libraryDependencies +="com.typesafe" % "config" % "1.3.4"
libraryDependencies +=  "com.typesafe.akka" %% "akka-actor" % "2.5.23"
libraryDependencies += "org.apache.poi" % "poi" % "3.17"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.17"

unmanagedJars in Compile += file(Path.userHome+"/lib/ojdbc6-11.2.0.3.jar")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "plugin.properties" => MergeStrategy.last
  case "log4j.properties" => MergeStrategy.last
  case "logback.xml" => MergeStrategy.last
  case "resources/logback.xml" => MergeStrategy.last
  case "resources/application.conf" => MergeStrategy.last
  case "application.conf" => MergeStrategy.last
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}


assemblyJarName in assembly :="xlsxloader.jar"
mainClass in (Compile, packageBin) := Some("loader.XLSXLoader")
mainClass in (Compile, run) := Some("loader.XLSXLoader")

