@file:JsExport

package sentinel

import keep.Cache
import kotlinx.JsExport
import lexi.LoggerFactory

class RegistrationSceneOptions(
    val api: EmailRegistrationApi,
    val cache: Cache,
    val logger: LoggerFactory
)