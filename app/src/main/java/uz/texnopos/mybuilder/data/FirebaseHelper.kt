package uz.texnopos.mybuilder.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.ui.builder.BuilderModel

class FirebaseHelper {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun setBuilderData(
        model: BuilderModel,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .set(model)
            .addOnSuccessListener {
                onSuccess.invoke("Data added successfully")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun getBuilderData(
        onSuccess: (builder: BuilderModel,msg:String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.toObject(BuilderModel::class.java)!!,"Data succesfully getted")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}