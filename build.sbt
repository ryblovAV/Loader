name := "Loader"

version := "1.0"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

libraryDependencies ++= Seq(
  "org.clapper" %% "grizzled-slf4j" % "1.0.2",
  "junit" % "junit" % "4.11",
  "org.hibernate" % "hibernate-core" % "4.3.8.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "org.springframework" % "spring-core" % "4.1.1.RELEASE",
  "org.springframework" % "spring-context" % "4.1.1.RELEASE",
  "org.springframework" % "spring-beans" % "4.1.1.RELEASE",
  "org.springframework" % "spring-tx" % "4.1.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "4.1.1.RELEASE",
  "org.springframework" % "spring-orm" % "4.1.1.RELEASE",
  "log4j" % "log4j" % "1.2.17",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.oracle" % "ojdbc14" % "10.2.0.4",
  //"com.oracle" % "ojdbc6" % "11.2.0.3.0",
  "org.scalatest" % "scalatest_2.10" % "2.0.M7"
)