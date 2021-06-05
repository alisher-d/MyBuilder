package uz.texnopos.mybuilder.ui.profile

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilder.databinding.ActivityVerifyBinding

class VerifyActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityVerifyBinding
    lateinit var btnVerify:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnVerify=binding.verifyBtn
        val storedVerificationId = intent.getStringExtra("storedVerificationId")!!
        val number=intent.getStringExtra("phoneNumber")
        val otpGiven = binding.idOtp
        val phone=binding.phone
        phone.text=number


        otpGiven.doOnTextChanged { text, start, before, count ->
            if (text?.length== 6) {
               isLoading(true)
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId, text.toString().trim())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                       isLoading(false)
                        if (task.isSuccessful) {
                            finish()
                        } else {
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }

    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            btnVerify.text="Checking..."
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            btnVerify.text="Confirm"
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

    }
}