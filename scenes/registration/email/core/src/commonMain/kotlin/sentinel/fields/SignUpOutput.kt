package sentinel.fields

import kase.catching
import kotlinx.serialization.Serializable
import neat.required
import sentinel.params.EmailSignUpParams

@Serializable
class SignUpOutput(
    var name: String? = "",
    var email: String? = ""
) {
    fun toParams() = catching {
        EmailSignUpParams(
            name = this::name.required,
            email = this::email.required
        )
    }
}