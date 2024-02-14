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
            api(projects.sentinelScenesRegistrationEmailCore)
            api(projects.sentinelScenesEnterpriseAuthenticationEmailCore)
            api(libs.sentinel.api.registration.email.test)
            api(libs.kommander.coroutines)
            api(libs.koncurrent.later.coroutines)
        }
    }
}
