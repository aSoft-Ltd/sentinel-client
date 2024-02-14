package sentinel

import kollections.first
import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import raven.EmailReceiver
import sentinel.params.EmailSignInParams
import kotlin.test.Test

abstract class EmailRegistrationFlowTest(
    private val receiver: EmailReceiver,
    private val signUp: SignUpScene,
    private val verification: VerificationScene,
    private val password: SetPasswordScene,
    private val signIn: SignInScene,
    private val authentication: EmailAuthenticationApi
) {

    @Test
    fun should_register_a_user() = runTest {
        val n = "Tester"
        val e = "test@test.com"
        signUp.form.fields.apply {
            name.set(n)
            email.set(e)
        }

        val message = receiver.anticipate()
        signUp.form.submit().await()
        val link = message.await().body.split(" ").last()
        verification.initialize(link) {}.await()
        password.form.fields.apply {
            password1.set(e)
            password2.set(e)
        }
        password.form.submit().await()

        signIn.form.fields.apply {
            email.set(e)
            password.set(e)
        }

        val res = signIn.form.submit().await()
        authentication.delete(EmailSignInParams(e,e)).await()
        expect(res.user.emails.first().value).toBe(e)
    }
}