package sentinel

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat

class RegistrationFlixApiConfig(
    val scope: CoroutineScope,
    val link: String,
    val client: HttpClient,
    val endpoint: RegistrationEndpoint,
    val codec: StringFormat
)