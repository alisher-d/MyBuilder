package uz.texnopos.mybuilder.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        val etEmail=binding.etEmail
        val etPassword=binding.etPassword
        val btnLogin=binding.btnLogin
        val inputEmail=binding.inputEmail
        val inputPassword=binding.inputPassword
        val gotoRegister=binding.gotoRegister
        val loading=binding.loading
        navController = Navigation.findNavController(view)
        etEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputEmail.error = "Please, enter the e-mail"
                btnLogin.isEnabled = false
            } else {
                inputEmail.error = null
                if (etPassword.text!!.isNotEmpty()) btnLogin.isEnabled = true
            }
        }
        etPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputPassword.error = "Please, enter the password"
                btnLogin.isEnabled = false
            } else {
                inputPassword.error = null
                if (etEmail.text!!.isNotEmpty()) btnLogin.isEnabled = true
            }
        }

       btnLogin.setOnClickListener {
            val email = etEmail.text
            val password = etPassword.text
            if (email!!.isNotEmpty() && password!!.isNotEmpty()) {
                loading.visibility = View.VISIBLE
                mAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener {
                        loading.visibility = View.GONE
                        if (it.isSuccessful) {
                            val currentUser = mAuth.currentUser
                            updateUI(currentUser)
                        } else {
                            Toast.makeText(
                                context,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }
gotoRegister.setOnClickListener {
    val intent= Intent(activity,RegisterActivity::class.java)
    startActivity(intent)
}
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            navController.navigate(R.id.action_navigation_profile_to_profileFragment)
        }
    }
}