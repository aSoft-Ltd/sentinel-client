package sentinel

import koncurrent.TODOLater
import sentinel.params.SignUpParams
import sentinel.params.UserAccountParams
import sentinel.params.VerificationParams

class RegistrationApiUnimplemented : RegistrationApi {
    override fun sendVerificationLink(email: String) = TODOLater()

    override fun signUp(params: SignUpParams) = TODOLater()

    override fun verify(params: VerificationParams) = TODOLater()

    override fun createUserAccount(params: UserAccountParams) = TODOLater()

}