package sentinel

import identifier.IdentifierSettings
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsName

interface ProfileApiProvider {
    val profile: ProfileApi

    @JsName("settingsWithData")
    fun <R> settings(data: R): Later<IdentifierSettings<R>>
    fun settings() = settings(null)
}