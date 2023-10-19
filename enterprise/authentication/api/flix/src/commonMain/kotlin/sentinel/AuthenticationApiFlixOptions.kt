package sentinel

import io.ktor.client.HttpClient
import keep.Cache
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.Logger

class AuthenticationApiFlixOptions(
    val scope: CoroutineScope,
    val link: String,
    val cache: Cache,
    val http: HttpClient,
    val logger: Logger,
    val endpoint: AuthenticationEndpoint,
    val codec: StringFormat
)