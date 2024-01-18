package sentinel

import kommander.expect
import kommander.expectFailure
import koncurrent.later.await
import kotlinx.coroutines.test.runTest
import sentinel.exceptions.UserNotRegisteredForAuthenticationException
import sentinel.params.EmailSignInParams
import kotlin.test.Test

abstract class EmailAuthenticationApiNegativeTest(private val authentication: EmailAuthenticationApi) {

    @Test
    fun should_fail_to_authenticate_a_none_registered_user() = runTest {
        val email = "petro@yohana.com"
        val exp = expectFailure {
            authentication.signIn(EmailSignInParams(email = email, password = "123456")).await()
        }
        expect(exp.message).toBe(UserNotRegisteredForAuthenticationException(email).message)
    }
}