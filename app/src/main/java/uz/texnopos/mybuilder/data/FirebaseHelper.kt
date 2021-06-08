package uz.texnopos.mybuilder.data

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import uz.texnopos.mybuilder.ui.builder.BuilderModel
import uz.texnopos.mybuilder.ui.builder.UserModel
import java.util.concurrent.TimeUnit

class FirebaseHelper {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getBuilderData(
        onSuccess: (builder: BuilderModel, msg: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObject(BuilderModel::class.java)!!, "Data succesfully getted")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getUserData(
        onSucces: (user: UserModel) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                onSucces.invoke(it.toObject(UserModel::class.java)!!)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun setUserData(
        user: UserModel,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                onSucces.invoke("Saved")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSucces.invoke("signInWithCredential:success")
                } else {
                    onFailure.invoke(task.exception?.localizedMessage)
                }
            }
    }

    fun checkUsername(entered: (hasUsername: Boolean) -> Unit) {
        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener {
                if (!it.result!!.exists()) {
                    entered.invoke(false)
                } else {
                    entered.invoke(true)
                }
            }
    }

    fun sendVerificationcode(
        activity: Activity,
        number: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun update(
        onSucces: (succes: Boolean) -> Unit,
    ) {
        if (auth.currentUser != null) onSucces(true)
        else onSucces(false)
    }
}