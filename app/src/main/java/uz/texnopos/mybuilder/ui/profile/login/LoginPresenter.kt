package uz.texnopos.mybuilder.ui.profile.login

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilder.data.FirebaseHelper

class LoginPresenter(val view: LoginView) {
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val dbHelper = FirebaseHelper()

    fun firebaseAuthWithGoogle(idToken: String) {
        dbHelper.firebaseAuthWithGoogle(idToken,
            {
                updateUI()
            },
            {
                view.showMessage(it)
                loading(false)
            })
    }

    fun checkUsername() {
        dbHelper.checkUsername {
            view.hasUsername(it)
        }
    }

    fun callback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
               updateUI()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                view.showMessage(e.message)
                loading(false)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
//                view.showMessage("onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                view.onCodeSent(storedVerificationId)
                loading(false)
            }
        }
    }

    fun sendVerificationcode(activity: Activity, number: String) {
        loading(true)
        dbHelper.sendVerificationcode(activity, number, callbacks)
    }

    fun loading(isLoading: Boolean) {
        view.loading(isLoading)
    }

    fun updateUI() {
        dbHelper.update {
            view.updateUI(it)
        }
    }
}