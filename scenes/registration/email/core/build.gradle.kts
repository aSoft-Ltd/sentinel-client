plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "kotlin multiplatform scenes registration"

kotlin {
    jvm { library() }
    js(IR) { library() }

    sourceSets {
        commonMain.dependencies {
            api(projects.sentinelApiRegistrationEmailCore)
            api(projects.sentinelScenesReceptionEmail)
            api(libs.cinematic.scene.core)
            api(libs.hormone.core)
            api(libs.lexi.api)
            api(libs.keep.api)
            api(libs.identifier.fields)
        }

        commonMain.dependencies {
            implementation(libs.kommander.coroutines)
        }
    }
}
