package sentinel

import koncurrent.later.await
import raven.EmailReceiver
import sentinel.params.EmailSignUpParams
import sentinel.params.EmailVerificationParams
import sentinel.params.UserAccountParams

suspend fun EmailRegistrationApi.register(
    receiver: EmailReceiver,
    name: String,
    email: String,
    password: String,
) {
    val params1 = EmailSignUpParams(name, email)
    val res = signUp(params1).await()

    val message = receiver.anticipate()

    sendVerificationLink(email).await()

    val token = message.await().body.split("=").last()

    verify(EmailVerificationParams(email = res.email, token = token)).await()

    createUserAccount(UserAccountParams(email, password, token)).await()
}