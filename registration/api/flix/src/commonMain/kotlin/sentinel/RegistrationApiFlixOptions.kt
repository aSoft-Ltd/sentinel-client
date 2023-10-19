package sentinel

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.Logger

class RegistrationApiFlixOptions(
    val scope: CoroutineScope,
    val link: String,
    val http: HttpClient,
    val logger: Logger,
    val endpoint: RegistrationEndpoint,
    val codec: StringFormat
)