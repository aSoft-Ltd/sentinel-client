package sentinel

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.Logger

class RegistrationFlixApiConfig(
    val scope: CoroutineScope,
    val link: String,
    val client: HttpClient,
    val logger: Logger,
    val endpoint: RegistrationEndpoint,
    val codec: StringFormat
)