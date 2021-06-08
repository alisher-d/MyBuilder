package uz.texnopos.mybuilder.ui.profile

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentUsernameBinding
import uz.texnopos.mybuilder.toast
import uz.texnopos.mybuilder.ui.builder.BuilderModel
import uz.texnopos.mybuilder.ui.builder.UserModel

class UsernameFragment : Fragment(R.layout.fragment_username) {
    lateinit var binding: FragmentUsernameBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    lateinit var preferences: SharedPreferences
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsernameBinding.bind(view)
        preferences = requireActivity().getSharedPreferences("username", Activity.MODE_PRIVATE)
       val etFirstName=binding.etFirstName
        val etLastname=binding.etLastName
        val etBirthday=binding.etBirthday
        val etPhone=binding.etPhone
        val etEmail=binding.etEmail
        val btnContinue=binding.btnContinue
        val loading=binding.loading
        navController = Navigation.findNavController(view)
        btnContinue.setOnClickListener {
            loading.visibility = View.VISIBLE
            val map = UserModel()
            map.firstName=etFirstName.text.toString()
            map.lastName=etLastname.text.toString()
            map.birthday=etBirthday.text.toString()
            map.phone=auth.currentUser!!.phoneNumber.toString()
            map.email=auth.currentUser!!.email.toString()
            db.collection("users").document(auth.currentUser!!.uid)
                .set(map)
                .addOnCompleteListener {
                    loading.visibility=View.GONE
                    if (it.isSuccessful) {
                        preferences.edit().putBoolean("checked", false).apply()
                        navController.navigate(R.id.action_navigation_username_to_navigation_profile)
                    } else {
                        it.exception?.message.toast(context)
                    }
                }
        }

    }
}