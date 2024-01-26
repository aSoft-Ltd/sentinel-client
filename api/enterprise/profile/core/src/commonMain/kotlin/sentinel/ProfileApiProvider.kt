package sentinel

import identifier.IdentifierSettings
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

interface ProfileApiProvider {
    val profile: ProfileApi

    fun <R> settings(data: R): Later<IdentifierSettings<R>>
    fun settings() = settings(null)
}