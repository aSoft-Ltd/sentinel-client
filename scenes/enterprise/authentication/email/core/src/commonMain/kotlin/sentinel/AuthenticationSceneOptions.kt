package sentinel

import keep.Cache
import lexi.LoggerFactory
import snitch.Snitch

class AuthenticationSceneOptions(
    val api: EmailAuthenticationApi,
    val logger: LoggerFactory,
    val cache: Cache,
    val toaster: Snitch
)