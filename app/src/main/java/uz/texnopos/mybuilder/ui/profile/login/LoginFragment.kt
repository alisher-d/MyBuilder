package uz.texnopos.mybuilder.ui.profile.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.Constants.RC_SIGN_IN
import uz.texnopos.mybuilder.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilder.Constants.TAG
import uz.texnopos.mybuilder.databinding.FragmentLoginBinding
import uz.texnopos.mybuilder.ui.profile.VerifyActivity
import java.util.concurrent.TimeUnit


class LoginFragment : BaseFragment(R.layout.fragment_login), LoginView {


    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private val presenter = LoginPresenter(this)
    private lateinit var bind: FragmentLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var navController: NavController
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentLoginBinding.bind(view)
        navController = Navigation.findNavController(view)
        bind.apply {
            etPhone.doOnTextChanged { text, _, _, _ ->
                when {
                    text!!.isEmpty() -> {
                        btnLogin.isEnabled = false
                    }
                    text.length == 9 -> {
                        etPhone.hideSoftKeyboard()
                        btnLogin.isEnabled = true
                    }
                    else -> {
                        inputEmail.error = null
                        btnLogin.isEnabled = false
                    }
                }
            }
            btnLogin.onClick {
                if (isNetworkAvailable()) {
                    val number = etPhone.textToString().trim()
                    sendVerificationcode("+998$number")
                    showProgress()
                }
            }
            googleLogin.onClick {
                if (isNetworkAvailable()) {
                    showProgress()
                    val signInIntent = googleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }
            }
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
               presenter.updateUI()
                hideProgress()
            }
            override fun onVerificationFailed(e: FirebaseException) {
                toast(e.message!!)
                hideProgress()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(context, VerifyActivity::class.java)
                intent.putExtra(USER_PHONE_NUMBER, "+998${bind.etPhone.textToString()}")
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                hideProgress()
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    }

    override fun onStart() {
        presenter.updateUI()
        super.onStart()
    }
    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                presenter.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                toast(e.message!!)
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun showMessage(message: String?) {
        toast(message!!)
    }

    override fun updateUI() {
        if (auth.currentUser!=null)
            when (getSharedPreferences().getIntValue("succes", 0)) {
                0 -> {
                    showProgress()
                    presenter.checkUsername()
                }
                1 -> {
                    hideProgress()
                    navController.navigate(R.id.action_navigation_login_to_navigation_profile)
                }
                -1 -> {
                    hideProgress()
                    navController.navigate(R.id.action_navigation_login_to_navigation_username)
                }
            }
        else (activity as MainActivity).navView.visibility=View.GONE
    }

}