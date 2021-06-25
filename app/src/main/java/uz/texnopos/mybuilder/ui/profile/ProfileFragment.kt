package uz.texnopos.mybuilder.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.databinding.FragmentProfileBinding
import uz.texnopos.mybuilder.ui.builder.BuilderActivity


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private lateinit var navController: NavController
    private lateinit var bind: FragmentProfileBinding
    private val mAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        bind = FragmentProfileBinding.bind(view)
        bind.createNew.onClick {
            val intent = Intent(context, BuilderActivity::class.java)
            startActivity(intent)
        }
        bind.userCV.onClick {
            val intent = Intent(context, BuilderActivity::class.java)
            startActivity(intent)
        }
        bind.signout.onClick {
            mAuth.signOut()
            getSharedPreferences().removeKey("succes")
            clearLoginPref()
            navController.navigate(R.id.action_navigation_profile_to_navigation_login)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).navView.visibility=View.VISIBLE
        bind.tvEmail.text = getEmail()
        bind.etFirstName.text = getFullName()
        bind.tvPhone.text = getPhoneNumber()
        if (getCreated()) {
            bind.userCV.visibility = View.VISIBLE
            bind.createNew.visibility = View.GONE
        } else {
            bind.userCV.visibility = View.INVISIBLE
            bind.createNew.visibility = View.VISIBLE
        }
    }
}