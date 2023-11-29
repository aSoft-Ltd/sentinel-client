package sentinel

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.LoggerFactory

class RegistrationApiFlixOptions(
    val scope: CoroutineScope,
    val link: String,
    val http: HttpClient,
    val logger: LoggerFactory,
    val endpoint: RegistrationEndpoint,
    val codec: StringFormat
)