package sentinel.transformers

import kase.catching
import neat.required
import sentinel.fields.SetPasswordOutput
import sentinel.params.UserAccountParams
import sentinel.params.EmailVerificationParams

fun SetPasswordOutput.toParams(params: EmailVerificationParams) = catching {
    UserAccountParams(
        loginId = params.email,
        password = this::password1.required,
        registrationToken = params.token
    )
}