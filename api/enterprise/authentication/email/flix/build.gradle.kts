plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform implementation for the sentinel client api following the flix pattern"

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        commonMain.dependencies {
            api(projects.sentinelApiEnterpriseAuthenticationEmailCore)
            api(libs.koncurrent.later.coroutines)
            api(ktor.client.core)
            api(libs.keep.api)
            api(libs.kase.response.ktor.client)
            api(libs.lexi.api)
        }
    }
}