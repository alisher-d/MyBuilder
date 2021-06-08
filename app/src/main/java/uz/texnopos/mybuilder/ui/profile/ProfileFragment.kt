package uz.texnopos.mybuilder.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.ui.builder.BuilderActivity
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentProfileBinding
import uz.texnopos.mybuilder.ui.builder.BuilderModel


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentProfileBinding
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    lateinit var etUsername: TextView
    lateinit var preferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentProfileBinding.bind(view)
        etUsername = binding.etFirstName
        val tvEmail=binding.tvEmail
        val tvPhone=binding.tvPhone
        val signout = binding.signout
        val btnCreateCv = binding.createCV
        val createNew=binding.createNew
        preferences=requireActivity().getSharedPreferences("username", Activity.MODE_PRIVATE)

        db.collection("builders").document(mAuth.currentUser!!.uid).get()
            .addOnCompleteListener {
                if (it.result!!.exists()){
                    btnCreateCv.visibility=View.VISIBLE
                    createNew.visibility=View.GONE
                }
                else{
                    btnCreateCv.visibility=View.INVISIBLE
                    createNew.visibility=View.VISIBLE
                }
            }
        createNew.setOnClickListener {
        val intent=Intent(context, BuilderActivity::class.java)
            startActivity(intent)
        }
        db.collection("users").document(mAuth.currentUser!!.uid).get()
            .addOnSuccessListener {
                etUsername.text = "${it.get("firstName").toString()} ${it.get("lastName").toString()}"
                tvEmail.text=it.get("email").toString()
                tvPhone.text=it.get("phone").toString()
            }

        signout.setOnClickListener {
            mAuth.signOut()
            preferences.edit().putBoolean("checked",true).apply()
            navController.navigate(R.id.action_navigation_profile_to_navigation_login)
        }
    }
}