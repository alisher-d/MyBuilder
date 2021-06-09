package uz.texnopos.mybuilder.ui.profile.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.Constants.SharedPref.HAS_USERNAME
import uz.texnopos.mybuilder.Constants.SharedPref.IS_LOGGED_IN
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentLoginBinding
import uz.texnopos.mybuilder.ui.profile.VerifyActivity


class LoginFragment : Fragment(R.layout.fragment_login), LoginView {
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var googleSignInClient: GoogleSignInClient
    private val presenter = LoginPresenter(this)
    private lateinit var binding: FragmentLoginBinding

    private lateinit var loading: RelativeLayout
    private lateinit var btnLogin: Button
    private lateinit var etPhone: TextInputEditText
    private lateinit var inputEmail: TextInputLayout
    private lateinit var navController: NavController
    lateinit var preferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        navController = Navigation.findNavController(view)
        preferences = requireActivity().getSharedPreferences("username", Activity.MODE_PRIVATE)
        loading = binding.loading
        etPhone = binding.etPhone
        btnLogin = binding.btnLogin
        inputEmail = binding.inputEmail

        val googleLogin = binding.googleLogin

        etPhone.doOnTextChanged { text, _, _, _ ->
            when {
                text!!.isEmpty() -> {
                    btnLogin.isEnabled = false
                }
                text.length == 9 -> btnLogin.isEnabled = true
                else -> {
                    inputEmail.error = null
                    btnLogin.isEnabled = false
                }
            }
        }
        btnLogin.onClick {
            btnLogin.text = "Sending..."
            val number = etPhone.text.toString().trim()
            presenter.sendVerificationcode(requireActivity(), "+998$number")
        }
        presenter.callback()
        googleLogin.onClick {
            googleSignIn()
        }
    }

    override fun onStart() {
        presenter.updateUI()
        super.onStart()
    }
    private fun googleSignIn() {
        presenter.loading(true)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                presenter.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
               toast(e.message!!)
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    override fun showMessage(message: String?) {
       toast(message!!)
    }

    override fun hasUsername(hasUsername: Boolean) {
        if (hasUsername) {
            navController.navigate(R.id.action_navigation_login_to_navigation_profile)
        }
        else {
            navController.navigate(R.id.action_navigation_login_to_navigation_username)
        }
    }

    override fun onPhoneVerificationFailed(e: FirebaseException) {
        btnLogin.text = "Get the code"
    }

    override fun loading(isLoading: Boolean) {
        if (isLoading) {
            this.loading.visibility = View.VISIBLE
            activity?.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            this.loading.visibility = View.GONE
            activity?.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onCodeSent(storedVerificationId: String) {
        val intent = Intent(context, VerifyActivity::class.java)
        intent.putExtra("phoneNumber", "+998${etPhone.text.toString()}")
        intent.putExtra("storedVerificationId", storedVerificationId)
        btnLogin.text = "Get the code"
        startActivity(intent)
    }

    override fun updateUI(succes: Boolean) {
        if (succes){
            if (getSharedPreferences().getIntValue("succes",0)==0){
                presenter.checkUsername()
            }
            else if (getSharedPreferences().getIntValue("succes",1)==1){
                navController.navigate(R.id.action_navigation_login_to_navigation_profile)
            }
            else if (getSharedPreferences().getIntValue("succes",-1)==-1){
                navController.navigate(R.id.action_navigation_login_to_navigation_username)
            }
        }
    }
}

