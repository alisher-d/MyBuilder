package uz.texnopos.mybuilder

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilder.databinding.ActivityVerifyBinding

class VerifyActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityVerifyBinding
    lateinit var loading:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val storedVerificationId = intent.getStringExtra("storedVerificationId")!!
        val verify = binding.verifyBtn
        val otpGiven = binding.idOtp
        loading = binding.loading
        verify.setOnClickListener {
            val otp = otpGiven.text.toString().trim()
            if (otp.isNotEmpty()) {
                startLoading()
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId, otp)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        stopLoading()
                        if (task.isSuccessful) {
                            finish()
                        } else {
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
        }

    }
    private fun startLoading() {
        loading.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun stopLoading() {
        loading.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}