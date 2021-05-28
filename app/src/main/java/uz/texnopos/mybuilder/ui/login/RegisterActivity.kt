package uz.texnopos.mybuilder.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.databinding.ActivityRegisterBinding

@Suppress("NAME_SHADOWING")
class RegisterActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityRegisterBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val etFirstName = binding.etFirstName
        val etLastName = binding.etLastName
        val etPhone = binding.etPhone
        val etEmail = binding.etEmail
        val etPassword = binding.etPassword
        val btnLogin = binding.btnRegister
        val inputFirstname = binding.inputFirstName
        val inputLastName = binding.inputLastName
        val inputPhone = binding.inputPhone
        val inputEmail = binding.inputEmail
        val inputPassword = binding.inputPassword
        val loading = binding.loading
        fun isNotEmpty(textInputLayout: TextInputLayout) {
            if (etFirstName.text!!.isNotEmpty() &&
                etLastName.text!!.isNotEmpty() &&
                etPhone.text!!.isNotEmpty() &&
                etEmail.text!!.isNotEmpty() &&
                etPassword.text!!.isNotEmpty()
            ) {
                btnLogin.isEnabled = true
            }
            textInputLayout.error = null
        }

        etFirstName.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputFirstname.error = "Required*"
                btnLogin.isEnabled = false
            } else isNotEmpty(inputFirstname)
        }
        etLastName.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputLastName.error = "Required*"
                btnLogin.isEnabled = false
            } else isNotEmpty(inputLastName)
        }
        etPhone.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputPhone.error = "Required*"
                btnLogin.isEnabled = false
            } else isNotEmpty(inputPhone)
        }
        etEmail.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputEmail.error = "Required*"
                btnLogin.isEnabled = false
            } else isNotEmpty(inputEmail)
        }
        etPassword.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                inputPassword.error = "Required*"
                btnLogin.isEnabled = false
            } else isNotEmpty(inputPassword)
        }
        btnLogin.setOnClickListener {
            val email = etEmail.text!!
            val password = etPassword.text!!
            val firstName = etFirstName.text!!
            val lastName = etLastName.text!!
            val phone = etPhone.text!!
            loading.visibility = View.VISIBLE
            mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        db.collection("users").document(mAuth.currentUser?.uid!!.toString()).get()
                            .addOnCompleteListener {
                                if (it.isSuccessful && !it.result?.exists()!!) {
                                    val map = mutableMapOf<String, Any?>()
                                    map["firstName"] = firstName.toString()
                                    map["lastName"] = lastName.toString()
                                    map["phone"] = phone.toString()
                                    map["email"] = mAuth.currentUser?.email
                                    map["password"] = password.toString()
                                    db.collection("users").document(mAuth.currentUser?.uid!!)
                                        .set(map)
                                        .addOnSuccessListener {
                                            loading.visibility = View.GONE
                                            Toast.makeText(this, "Your profil has been created successfully!", Toast.LENGTH_LONG).show()
                                            val user = mAuth.currentUser
                                            updateUI(user)
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                                        }
                                }
                            }
                    } else {
                        loading.visibility = View.GONE
                        Toast.makeText(this, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
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