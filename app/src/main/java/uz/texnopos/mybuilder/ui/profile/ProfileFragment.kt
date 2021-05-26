package uz.texnopos.mybuilder.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {
private lateinit var navController:NavController
private lateinit var binding:FragmentProfileBinding
private val mAuth=FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        binding= FragmentProfileBinding.bind(view)
        val signout=binding.signout
        signout.setOnClickListener {
            mAuth.signOut()
            navController.navigate(R.id.action_profileFragment_to_navigation_profile)
        }
    }
}