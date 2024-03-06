package sentinel

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.LoggerFactory

class EmailRegistrationScopedApiFlixOptions(
    val scope: CoroutineScope,
    val link: String,
    val http: HttpClient,
    val resolver: Resolver,
    val logger: LoggerFactory,
    val endpoint: EmailRegistrationEndpoint,
    val codec: StringFormat,
    val meta: String
)