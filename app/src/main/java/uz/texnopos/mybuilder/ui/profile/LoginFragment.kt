package uz.texnopos.mybuilder.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
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
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentLoginBinding
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment(R.layout.fragment_login) {
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var firebaseHelper: FirebaseHelper
    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var storedVerificationId: String

    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var googleSignInClient: GoogleSignInClient
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
        firebaseHelper = FirebaseHelper()
        preferences = requireActivity().getSharedPreferences("username", Activity.MODE_PRIVATE)
        loading = binding.loading
        etPhone = binding.etPhone
        btnLogin = binding.btnLogin
        inputEmail = binding.inputEmail
        val googleLogin = binding.googleLogin

        etPhone.doOnTextChanged { text, _, _, _ ->
            when {
                text!!.isEmpty() -> {
//                    inputEmail.error = "Please, enter your phone number!"
                    btnLogin.isEnabled = false
                }
                text.length == 9 -> btnLogin.isEnabled = true
                else -> {
                    inputEmail.error = null
                    btnLogin.isEnabled = false
                }
            }
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                checkUsername(mAuth.currentUser)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                btnLogin.text = "Get the code"
                isLoading(false)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                val intent = Intent(context, VerifyActivity::class.java)
                intent.putExtra("phoneNumber","+998${etPhone.text.toString()}")
                intent.putExtra("storedVerificationId", storedVerificationId)
//                val bundle=Bundle()
//                bundle.putString("storedVerificationId",storedVerificationId)
//                navController.navigate(R.id.action_navigation_login_to_verifyActivity,bundle)
                btnLogin.text = "Get the code"
                isLoading(false)
                startActivity(intent)
            }
        }

        btnLogin.setOnClickListener {
            isLoading(true)
            btnLogin.text = "Sending..."
            val number = etPhone.text.toString().trim()
            sendVerificationcode("+998$number")
        }
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // [END config_signin]
        googleLogin.setOnClickListener {
            isLoading(true)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    override fun onStart() {
        val currentUser = mAuth.currentUser
        checkUsername(currentUser)
        super.onStart()
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    checkUsername(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(),
                        task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    checkUsername(null)
                }
            }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
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

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            navController.navigate(R.id.action_navigation_login_to_navigation_profile)
            isLoading(false)
            preferences.edit().putBoolean("checked", false).apply()
        }
    }

    fun checkUsername(user: FirebaseUser?) {
        if (user != null) {
            if (preferences.getBoolean("checked", true)) {
                db.collection("users").document(user.uid).get()
                    .addOnCompleteListener {
                        if (!it.result!!.exists()) {
                            navController.navigate(R.id.action_navigation_login_to_navigation_username)
                            isLoading(false)
                        } else {
                            updateUI(user)
                        }
                        return@addOnCompleteListener
                    }
            } else {
                updateUI(user)
            }
        }
        else isLoading(false)
    }
}

