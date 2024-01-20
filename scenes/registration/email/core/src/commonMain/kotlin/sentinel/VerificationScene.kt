@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package sentinel

import cinematic.LazyScene
import kase.Failure
import kase.Loading
import kase.Pending
import kase.Result
import kase.Success
import kase.toLazyState
import koncurrent.Later
import koncurrent.later.andThen
import koncurrent.later.finally
import kotlinx.JsExport
import sentinel.params.EmailVerificationParams
import sentinel.tools.loadEmailSignUpParams
import sentinel.tools.removeEmailSignUpParams
import sentinel.tools.save

class VerificationScene(
    private val options: RegistrationSceneOptions
) : LazyScene<EmailVerificationParams>(Pending) {

    private val api = options.api

    private val cache = options.cache

    fun initialize(
        link: String,
        onCompleted: (Result<EmailVerificationParams>) -> Unit
    ): Later<Any> = cache.loadEmailSignUpParams().andThen { params ->
        ui.value = Loading(message = "Verifying your account (${params.email}), please wait . . . ")
        api.verify(EmailVerificationParams(params.email, parseToken(link).getOrThrow()))
    }.andThen {
        cache.removeEmailSignUpParams()
        cache.save(it)
    }.finally {
        onCompleted(it)
        ui.value = it.toLazyState()
    }

    companion object {
        internal fun parseToken(link: String): Result<String> {
            val residue = link.split("?").getOrNull(1) ?: return Failure(TOKEN_NOT_FOUND_IN_LINK)

            val queryParams = residue.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }
            val token = queryParams["token"] ?: return Failure(TOKEN_NOT_FOUND_IN_LINK)
            return Success(token)
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain verification token")
    }
}