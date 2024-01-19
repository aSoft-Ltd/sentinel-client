package sentinel.transformers

import sentinel.params.EmailSignInParams
import sentinel.params.UserAccountParams

fun UserAccountParams.toEmailSignInParams() = EmailSignInParams(
    email = loginId,
    password = password
)