package uz.texnopos.mybuilder.ui.profile.login

import com.google.firebase.FirebaseException

interface LoginView {
    fun showMessage(message: String?)
    fun hasUsername(hasUsername: Boolean)
    fun onPhoneVerificationFailed(e: FirebaseException)
    fun loading(isLoading:Boolean)
    fun onCodeSent(storedVerificationId:String)
    fun updateUI(succes:Boolean)
}