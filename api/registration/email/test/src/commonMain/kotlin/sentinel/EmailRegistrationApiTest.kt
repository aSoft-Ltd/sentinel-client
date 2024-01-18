package sentinel

import kommander.expect
import kommander.expectFailure
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import raven.EmailReceiver
import sentinel.exceptions.InvalidTokenForRegistrationException
import sentinel.exceptions.UserWithEmailAlreadyBeganRegistrationException
import sentinel.exceptions.UserWithEmailAlreadyCompletedRegistrationException
import sentinel.exceptions.UserWithEmailDidNotBeginRegistrationException
import sentinel.params.EmailSignUpParams
import sentinel.params.EmailVerificationParams
import kotlin.test.Test

abstract class EmailRegistrationApiTest(private val api: EmailRegistrationApi, private val receiver: EmailReceiver) {
    @Test
    fun should_fail_to_sign_up_an_already_verified_account() = runTest {
        api.signUp(EmailSignUpParams("Anderson", "andy@lamax.com")).await()
        val exp = expectFailure {
            api.signUp(EmailSignUpParams("Anderson", "andy@lamax.com")).await()
        }
        api.abort("andy@lamax.com").await()
        expect(exp.message).toBe(UserWithEmailAlreadyBeganRegistrationException("andy@lamax.com").message)
    }

    @Test
    fun should_be_able_to_complete_registration() = runTest {
        val params1 = EmailSignUpParams("Tony Stark", "tony@stark.com")
        val res = api.signUp(params1).await()

        val email = receiver.anticipate()
        api.sendVerificationLink("tony@stark.com").await()
        val token = email.await().body.split("=").last()

        api.verify(EmailVerificationParams(email = res.email, token = token)).await()

        val exp = expectFailure { api.signUp(params1).await() }
        api.abort(params1.email).await()
        expect(exp.message).toBe(UserWithEmailAlreadyCompletedRegistrationException(params1.email).message)
    }

    @Test
    fun should_fail_to_send_an_email_verification_for_a_user_who_has_not_began_the_registration_process() = runTest {
        val email = "john.me@yahoo.com"
        val exp = expectFailure { api.sendVerificationLink(email).await() }
        expect(exp.message).toBe(UserWithEmailDidNotBeginRegistrationException(email).message)
    }

    @Test
    fun should_fail_verification_with_an_invalid_token() = runTest {
        val params = EmailVerificationParams(email = "burns@bucky.com", token = "garbage")
        api.signUp(EmailSignUpParams("Bucky Barnes", params.email)).await()
        api.sendVerificationLink(params.email).await()
        val exp = expectFailure { api.verify(params).await() }
        api.abort(params.email).await()
        expect(exp.message).toBe(InvalidTokenForRegistrationException(params.token).message)
    }
}