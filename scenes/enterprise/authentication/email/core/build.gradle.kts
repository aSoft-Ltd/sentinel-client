plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }
//    if (Targeting.OSX) osxTargets() else listOf()
//    if (Targeting.NDK) ndkTargets() else listOf()
//    if (Targeting.LINUX) linuxTargets() else listOf()
//    if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        commonMain.dependencies {
            api(projects.sentinelScenesReceptionEmail)
            api(libs.sentinel.api.enterprise.authentication.email.core)
            api(libs.keep.api)
            api(libs.identifier.fields)
            api(libs.cinematic.scene.core)
            api(libs.snitch.api)
        }
    }
}
