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
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import koncurrent.later.finally
import sentinel.params.VerificationParams
import sentinel.tools.loadSignUpParams
import sentinel.tools.removeSignUpParams
import sentinel.tools.save
import kotlinx.JsExport

class VerificationScene(
    private val config: RegistrationSceneConfig<RegistrationApi>
) : LazyScene<VerificationParams>(Pending) {

    private val api = config.api

    private val cache = config.cache

    fun initialize(
        link: String,
        onCompleted: (Result<VerificationParams>) -> Unit
    ): Later<Any> {
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

        internal val QUERY_PARAMS_NOT_PROVIDED = IllegalArgumentException("Query params where not provided")
        internal val TOKEN_NOT_FOUND_IN_LINK = IllegalArgumentException("Could not obtain verification token")

        internal fun parseUrlToEmailVerificationParams(url: String): VerificationParams {
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

            return VerificationParams(email=email, token=token)
        }

        internal fun decodeUrlComponent(value: String): String {
            return value.replace("+", " ").replace("%([0-9A-Fa-f]{2})".toRegex()) {
                it.groupValues[1].toInt(16).toChar().toString()
            }
        }
    }
}