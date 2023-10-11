package sentinel

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.Logger

class AuthenticationApiFlixConfig(
    val scope: CoroutineScope,
    val link: String,
    val client: HttpClient,
    val logger: Logger,
    val endpoint: AuthenticationEndpoint,
    val codec: StringFormat
)