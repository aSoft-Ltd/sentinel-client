import java.io.File

pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "lexi", "neat", "kash-api", "geo-api", "kase", "keep", "cinematic",
    "kronecker", "epsilon-api", "krono-core", "hormone", "identifier-api",
    "kommerce", "kollections", "koncurrent", "kommander", "cabinet-api", 
    "snitch", "sentinel-core","identifier-client", "krono-client", "geo-client",
    "epsilon-client"
).forEach { includeBuild("../$it") }

rootProject.name = "sentinel-client"

includeSubs(base = "sentinel-reception-sdk-client", path = "reception/sdk", "core")
includeSubs(base = "sentinel-registration-sdk-client", path = "registration/sdk", "core")
includeSubs(base = "sentinel-enterprise-authentication-sdk-client", path = "enterprise/authentication/sdk", "core")
includeSubs(base = "sentinel-enterprise-profile-sdk-client", path = "enterprise/profile/sdk", "core")
includeSubs(base = "sentinel-enterprise-onboarding-sdk-client", path = "enterprise/onboarding/sdk", "core")
