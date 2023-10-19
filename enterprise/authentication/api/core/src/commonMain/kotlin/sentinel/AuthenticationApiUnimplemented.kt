package sentinel

import koncurrent.TODOLater
import sentinel.params.PasswordResetParams
import sentinel.params.SignInParams

class AuthenticationApiUnimplemented : AuthenticationApi {
    override fun session() = TODOLater()

    override fun signOut() = TODOLater()

    override fun sendPasswordResetLink(email: String) = TODOLater()

    override fun signIn(params: SignInParams) = TODOLater()

    override fun resetPassword(params: PasswordResetParams) = TODOLater()

}