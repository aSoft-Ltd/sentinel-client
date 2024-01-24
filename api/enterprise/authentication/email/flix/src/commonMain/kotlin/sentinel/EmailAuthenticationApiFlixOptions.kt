package sentinel

import io.ktor.client.HttpClient
import keep.Cache
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.StringFormat
import lexi.LoggerFactory

class EmailAuthenticationApiFlixOptions(
    val scope: CoroutineScope,
    val link: String,
    val cache: Cache,
    val http: HttpClient,
    val logger: LoggerFactory,
    val endpoint: EmailAuthenticationEndpoint,
    val codec: StringFormat,
    val sessionCacheKey: String,
    val meta: String
)