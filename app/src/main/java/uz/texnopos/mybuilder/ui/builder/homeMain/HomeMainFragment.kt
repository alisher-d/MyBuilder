package uz.texnopos.mybuilder.ui.builder.homeMain

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.databinding.FragmentHomeMainBinding

class HomeMainFragment : BaseFragment(R.layout.fragment_home_main) {
    private lateinit var binding: FragmentHomeMainBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeMainBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.apply {
            tvEmail.text = getEmail()
            tvUserName.text = getFullName()
            tvPhone.text = getPhoneNumber()
            if (getSelectableJobs().isNotEmpty()) {
                ln2.visibility = View.VISIBLE
                addProfession.visibility = View.GONE
                tvProfession.text = getProfession()
            } else {
                ln2.visibility = View.GONE
                addProfession.visibility = View.VISIBLE
            }
            if (getAddress() != "") {
                ln3.visibility = View.VISIBLE
                addAddress.visibility = View.GONE
                tvAddress.text = getAddress()
            } else {
                ln3.visibility = View.GONE
                addAddress.visibility = View.VISIBLE
            }
            if (getDescription() != "") {
                ln4.visibility = View.VISIBLE
                addYourself.visibility = View.GONE
                tvDescription.text =if (getDescription().length>60) "${getDescription().substring(0, 60)}..."
                else getDescription()
            } else {
                ln4.visibility = View.GONE
                addYourself.visibility = View.VISIBLE
            }
            editPersonalInfo.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_personalInfoFragment)
            }
            editProfession.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_professionFragment)
            }
            addProfession.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_professionFragment)

            }
            editAddress.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_addressFragment)
            }
            addAddress.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_addressFragment)
            }
            editYourself.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_selfFragment)
            }
            addYourself.onClick {
                navController.navigate(R.id.action_homeMainFragment_to_selfFragment)
            }
        }
    }
}