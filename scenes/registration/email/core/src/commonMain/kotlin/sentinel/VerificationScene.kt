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
import sentinel.params.EmailSignUpParams
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
    ): Later<EmailVerificationParams> {
//        val emailSignup = parseUrlToEmailSignupParams(link)
        val params = parseUrlToEmailVerificationParams(link)
        ui.value = Loading(message = "Verifying your account (${params.email}), please wait . . . ")

        return api.verify(params).finally {
            onCompleted(it)
            ui.value = it.toLazyState()
        }
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

        internal fun parseUrlToEmailSignupParams(url: String): EmailSignUpParams {
            val query = url.substringAfter("?", "")
            val queryPairs = query.split("&").mapNotNull {
                val parts = it.split("=")
                if (parts.size == 2) {
                    val key = parts[0]
                    val value = decodeUrlComponent(parts[1])
                    key to value
                } else {
                    null
                }
            }.toMap()

            val email = queryPairs["email"] ?: throw Exception("Failed to read email")
            val name = queryPairs["name"] ?: throw Exception("Failed to read name")

            return EmailSignUpParams(email=email, name=name)
        }
        internal fun parseUrlToEmailVerificationParams(url: String): EmailVerificationParams {
            val query = url.substringAfter("?", "")
            val queryPairs = query.split("&").mapNotNull {
                val parts = it.split("=")
                if (parts.size == 2) {
                    val key = parts[0]
                    val value = decodeUrlComponent(parts[1])
                    key to value
                } else {
                    null
                }
            }.toMap()

            val email = queryPairs["email"] ?: throw Exception("Failed to read email")
            val token = queryPairs["token"] ?: throw Exception("Failed to read token")

            return EmailVerificationParams(email=email, token=token)
        }

        internal fun decodeUrlComponent(value: String): String {
            return value.replace("+", " ").replace("%([0-9A-Fa-f]{2})".toRegex()) {
                it.groupValues[1].toInt(16).toChar().toString()
            }
        }

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain verification token")
    }
}