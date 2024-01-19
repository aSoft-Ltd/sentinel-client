@file:JsExport

package sentinel

import kotlinx.JsExport
import keep.Cache
import lexi.LoggerFactory

class RegistrationSceneOptions<out A>(
    val api: A,
    val cache: Cache,
    val logger: LoggerFactory
) {
    fun <R> map(transformer: (A) -> R) = RegistrationSceneOptions(transformer(api), cache, logger)
}