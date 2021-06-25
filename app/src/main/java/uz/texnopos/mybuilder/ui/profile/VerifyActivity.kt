package uz.texnopos.mybuilder.ui.profile

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import uz.texnopos.mybuilder.AppBaseActivity
import uz.texnopos.mybuilder.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilder.databinding.ActivityVerifyBinding
import uz.texnopos.mybuilder.toast

class VerifyActivity : AppBaseActivity() {
    private val auth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityVerifyBinding
    lateinit var btnVerify: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnVerify = binding.verifyBtn
        val storedVerificationId = intent.getStringExtra("storedVerificationId")!!
        val number = intent.getStringExtra(USER_PHONE_NUMBER)
        val otpGiven = binding.idOtp
        val phone = binding.phone
        phone.text = number


        otpGiven.doOnTextChanged { text, start, before, count ->
            if (text?.length == 6) {
                showProgress(true)
                val credential: PhoneAuthCredential =
                    PhoneAuthProvider.getCredential(storedVerificationId, text.toString().trim())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        showProgress(false)
                        if (task.isSuccessful) {
                            finish()
                        } else {
                            if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                toast("Invalid OTP")
                            }
                        }
                    }
            }
        }

    }
}