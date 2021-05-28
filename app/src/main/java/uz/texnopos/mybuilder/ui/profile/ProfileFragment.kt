package uz.texnopos.mybuilder.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import uz.texnopos.mybuilder.LoginActivity
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentProfileBinding
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    lateinit var etFirstName: TextInputEditText
    lateinit var etLastName: TextInputEditText
    lateinit var etPhone: TextInputEditText
    lateinit var loading:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection("users").document(mAuth.currentUser!!.uid).get()
            .addOnSuccessListener {
                etFirstName.setText(it.get("firstName").toString())
                etLastName.setText(it.get("lastName").toString())
                etPhone.setText(it.get("phone").toString())
                loading.visibility = View.GONE
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentProfileBinding.bind(view)
        etFirstName = binding.etFirstName
        etLastName = binding.etLastName
        etPhone = binding.etPhone
        val signout = binding.signout
        val btnEdit = binding.btnEdit
        val inputLastName = binding.inputLastName
        val inputFirstName = binding.inputFirstName
        val inputPhone = binding.inputPhone
        val btnUpdate = binding.btnUpdate
        loading = binding.loading

        btnEdit.setOnClickListener {
            btnEdit.isEnabled = false
            signout.isEnabled = false
            btnUpdate.isEnabled = true
            etFirstName.isEnabled = true
            etLastName.isEnabled = true
            etPhone.isEnabled = true
            inputFirstName.endIconMode = TextInputLayout.END_ICON_CUSTOM
            inputLastName.endIconMode = TextInputLayout.END_ICON_CUSTOM
            inputPhone.endIconMode = TextInputLayout.END_ICON_CUSTOM
        }
        btnUpdate.setOnClickListener {
            loading.visibility = View.VISIBLE

            val map = mutableMapOf<String, Any?>()
            map["firstName"] = etFirstName.text.toString()
            map["lastName"] = etLastName.text.toString()
            map["phone"] = etPhone.text.toString()
            db.collection("users").document(mAuth.currentUser?.uid!!)
                .set(map)
                .addOnSuccessListener {
                    loading.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Your profile has been successfully changed ",
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
        signout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}