package uz.texnopos.mybuilder.ui.profile.login

import uz.texnopos.mybuilder.data.FirebaseHelper

class LoginPresenter(val view: LoginView) {
    private val dbHelper = FirebaseHelper()

    fun firebaseAuthWithGoogle(idToken: String) {
        dbHelper.firebaseAuthWithGoogle(idToken,
            {
                updateUI()
            },
            {
                view.showMessage(it)
            })
    }

    fun checkUsername() {
        dbHelper.getUserData {
            updateUI()
        }
    }


    fun updateUI() {
        view.updateUI()
    }
}