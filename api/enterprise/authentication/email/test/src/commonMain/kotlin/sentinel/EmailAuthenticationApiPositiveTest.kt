package sentinel

import kommander.expect
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import raven.EmailReceiver
import sentinel.params.PasswordResetParams
import sentinel.params.EmailSignInParams
import sentinel.params.EmailVerificationParams
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds

abstract class EmailAuthenticationApiPositiveTest(
    private val registration: EmailRegistrationApi,
    private val authentication: EmailAuthenticationApi,
    private val receiver: EmailReceiver
) {

    @Test
    fun should_be_able_to_sign_in_with_a_valid_credential() = runTest {
        val email = "andy@lamax.com"
        registration.register(receiver, name = "Anderson", email = email, password = email)
        val params = EmailSignInParams(email, email)
        val res = authentication.signIn(params).await()
        authentication.delete(params).await()
        expect(res.user.name).toBe("Anderson")
    }

    @Test
    fun should_be_able_to_request_the_current_session_from_a_token() = runTest {
        val email = "john@doe.com"
        registration.register(receiver, name = "John Doe", email = email, password = email)
        val params = EmailSignInParams(email,email)
        val session1 = authentication.signIn(params).await()
        val session2 = authentication.session().await()
        authentication.delete(params).await()
        expect(session1.secret).toBe(session2.secret)
    }

    @Test
    fun should_be_able_to_recover_a_user_password() = runTest(timeout = 30.seconds) {
        val email = "jane@doe.com"
        registration.register(receiver, name = "Jane Doe", email = email, password = email)
        val mail = receiver.anticipate()
        authentication.sendPasswordResetLink("jane@doe.com").await()
        val token = mail.await().body.split("=").last()
        authentication.resetPassword(PasswordResetParams("new", token)).await()
        authentication.signIn(EmailSignInParams(email, "new")).await()
        authentication.delete(EmailSignInParams(email, "new")).await()
    }
}