package uz.texnopos.mybuilder.data

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class Functions {
    private val functions: FirebaseFunctions = Firebase.functions
    fun addMessage(text: String): Task<String> {
        val data = hashMapOf("text" to text, "push" to true)
        return functions
            .getHttpsCallable("addMessage")
            .call(data)
            .continueWith { task ->
                val result = task.result!!.data as String
                result
            }
    }
}