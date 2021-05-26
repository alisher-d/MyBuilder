package uz.texnopos.mybuilder.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import uz.texnopos.mybuilder.databinding.ActivityRegisterBinding

@Suppress("NAME_SHADOWING")
class RegisterActivity : AppCompatActivity() {
private val mAuth=FirebaseAuth.getInstance()
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val etEmail = binding.etEmail
        val etPassword = binding.etPassword
        val btnLogin = binding.btnLogin
        val inputEmail = binding.inputEmail
        val inputPassword = binding.inputPassword
        val loading = binding.loading
        etEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputEmail.error = "Please, enter the e-mail"
                btnLogin.isEnabled = false
            } else {
                inputEmail.error = null
                if (etPassword.text!!.isNotEmpty()) btnLogin.isEnabled = true
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
        }
        btnLogin.setOnClickListener {
            val email = etEmail.text
            val password = etPassword.text
            loading.visibility=View.VISIBLE
            if (email!!.isNotEmpty() && password!!.isNotEmpty()) {
                loading.visibility = View.VISIBLE
                mAuth.createUserWithEmailAndPassword(email.toString(),password.toString())
                    .addOnCompleteListener {
                        loading.visibility=View.GONE
                        if (it.isSuccessful){
                            val user=mAuth.currentUser
                            updateUI(user)
                        }
                        else
                            Toast.makeText(this, "Authentication is failed!", Toast.LENGTH_SHORT).show()
                    }
            }

        }


    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            finish()
        }
    }

}