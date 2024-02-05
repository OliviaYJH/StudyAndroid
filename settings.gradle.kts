pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StudyAndroid"
include(":app")
include(":app:navigationgraph")
include(":app:binding")
include(":app:lifecycle")
include(":app:countnum")
include(":app:widget")
include(":app:stopwatch")
include(":app:wordbook")
include(":app:myframe")
include(":app:expandableanimation")
include(":app:musicplayer")
