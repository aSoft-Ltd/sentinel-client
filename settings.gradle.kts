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
    "snitch", "sentinel-core", "identifier-client", "krono-client", "geo-client",
    "epsilon-client", "raven-core"
).forEach { includeBuild("../$it") }

rootProject.name = "sentinel-client"

includeSubs(base = "sentinel-api-registration", "api/registration", "core")
includeSubs(base = "sentinel-api-registration-email", "api/registration/email", "core", "test", "flix")
includeSubs(base = "sentinel-api-registration-phone", "api/registration/phone", "core", "test", "flix")

includeSubs(base = "sentinel-api-enterprise-authentication", "api/enterprise/authentication", "core")
includeSubs(base = "sentinel-api-enterprise-authentication-email", "api/enterprise/authentication/email", "core", "test", "flix")

includeSubs(base = "sentinel-api-enterprise-profile", "api/enterprise/profile", "core")

includeSubs(base = "sentinel-scenes-reception", "scenes/reception", "email")
includeSubs(base = "sentinel-scenes-registration-email", "scenes/registration/email", "core", "test")
includeSubs(base = "sentinel-scenes-enterprise-authentication-email", "scenes/enterprise/authentication/email", "core", "test")
includeSubs(base = "sentinel-scenes-enterprise-onboarding", "scenes/enterprise/onboarding", "core")
includeSubs(base = "sentinel-scenes-enterprise-profile", "scenes/enterprise/profile", "core")

// deprecated dependencies
includeSubs(base = "sentinel-reception-sdk-client", path = "reception/sdk", "core")
includeSubs(base = "sentinel-registration-api", path = "registration/api", "core", "flix")
includeSubs(base = "sentinel-registration-sdk-client", path = "registration/sdk", "core")
includeSubs(base = "sentinel-enterprise-authentication-api", path = "enterprise/authentication/api", "core", "flix")
includeSubs(base = "sentinel-enterprise-authentication-sdk-client", path = "enterprise/authentication/sdk", "core")
includeSubs(base = "sentinel-enterprise-profile-sdk-client", path = "enterprise/profile/sdk", "core")
includeSubs(base = "sentinel-enterprise-onboarding-sdk-client", path = "enterprise/onboarding/sdk", "core")
