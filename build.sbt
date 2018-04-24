lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        libraryDependencies ++=
            "com.android.support" % "appcompat-v7" % "27.1.1" ::
            Nil,
        name := "vertical-stepper-view",
        publishArtifact in ( Compile, packageDoc ) := false
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )