package uz.texnopos.mybuilder.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.Constants.SharedPref.ADDRESS
import uz.texnopos.mybuilder.Constants.SharedPref.CREATED
import uz.texnopos.mybuilder.Constants.SharedPref.DESCRIPTION
import uz.texnopos.mybuilder.Constants.SharedPref.PROFESSION
import uz.texnopos.mybuilder.Constants.SharedPref.PUBLISH
import uz.texnopos.mybuilder.Constants.SharedPref.SELECTABLE_JOBS
import uz.texnopos.mybuilder.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilder.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilder.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilder.getSharedPreferences
import uz.texnopos.mybuilder.models.BuilderModel
import uz.texnopos.mybuilder.models.JobModel
import uz.texnopos.mybuilder.models.UserModel
import java.lang.ref.Reference

class FirebaseHelper {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    fun setUserData(
        user: UserModel,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("users").document(auth.currentUser!!.uid)
            .set(user)
            .addOnSuccessListener {
                saveUserData(user)
                onSucces.invoke("Saved")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun updatePersonalData(
        user: UserModel,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .update("personal",user)
            .addOnSuccessListener {
                saveUserData(user)
                onSucces.invoke("Saved")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun updateBuilderJobs(
        list: ArrayList<String>,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .update(
                mapOf(
                    "selectableJobs" to list,
                    "profession" to list.first()
                )
            )
            .addOnSuccessListener {
                onSucces.invoke("Updated")
                getSharedPreferences().setValue(SELECTABLE_JOBS, list)
                getSharedPreferences().setValue(PROFESSION, list.first())
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun updateBuilderAddress(
        address: BuilderModel.Address,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .update("address", address)
            .addOnSuccessListener {
                onSucces.invoke("Saved")
                getSharedPreferences().setValue(
                    ADDRESS,
                    "${address.countryName}\n${address.stateName}\n${address.cityName}"
                )
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun updateBuilderDescription(
        text: String,
        onSucces: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").document(auth.currentUser!!.uid)
            .update("description", text)
            .addOnSuccessListener {
                onSucces.invoke("Saved")
                getSharedPreferences().setValue(DESCRIPTION, text)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }

    }

    fun getUserData(onComplete: () -> Unit) {
        db.collection("builders").document(auth.currentUser!!.uid).get()
            .addOnCompleteListener {
                if (!it.result!!.exists()) {
                    getSharedPreferences().setValue("succes", -1)

                } else {
                    val user = it.result!!.toObject(BuilderModel::class.java)!!
                    getSharedPreferences().setValue("succes", 1)
                    saveUserData(user.personal)

                }
                onComplete.invoke()
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

    fun getJobList(
        onSucces: (msg: ArrayList<JobModel>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("jobList")
            .get()
            .addOnSuccessListener {
                val list= arrayListOf<JobModel>()
                for (i in it){
                    list.add(JobModel(i["image"].toString(),i.id))
                }
                onSucces.invoke(list)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }


    }

    fun getBuilders(
        onSucces: (builders: MutableList<BuilderModel>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection("builders").whereEqualTo("published", true)
            .get()
            .addOnSuccessListener {
                val builders = mutableListOf<BuilderModel>()
                for (doc in it.documents) {
                    builders.add(doc.toObject(BuilderModel::class.java)!!)
                }
                onSucces.invoke(builders)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    private fun saveUserData(user: UserModel) {
        getSharedPreferences().setValue(USER_FULL_NAME, user.fullName)
        getSharedPreferences().setValue(USER_PHONE_NUMBER, user.phone)
        getSharedPreferences().setValue(USER_EMAIL, user.email)

    }
    private fun saveBuilderData(builder:BuilderModel){
        getSharedPreferences().setValue(CREATED, builder.created)
        getSharedPreferences().setValue(PUBLISH, builder.published)
        getSharedPreferences().setValue(PROFESSION, builder.profession)
        getSharedPreferences().setValue(ADDRESS, builder.address)
        getSharedPreferences().setValue(DESCRIPTION, builder.description)
        getSharedPreferences().setValue(SELECTABLE_JOBS, builder.selectableJobs)
    }
}