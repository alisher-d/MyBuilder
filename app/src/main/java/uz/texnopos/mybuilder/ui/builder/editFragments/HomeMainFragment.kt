package uz.texnopos.mybuilder.ui.builder.editFragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentHomeMainBinding


class HomeMainFragment : Fragment(R.layout.fragment_home_main) {
    private lateinit var binding:FragmentHomeMainBinding
    lateinit var editAddress:LinearLayout
    lateinit var editPersonalInfo:LinearLayout
    lateinit var editProfession:LinearLayout
    lateinit var editYourself:LinearLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeMainBinding.bind(view)
        val navController=Navigation.findNavController(view)
        binding.apply {
           this@HomeMainFragment.editAddress = this.editAddress
            this@HomeMainFragment.editPersonalInfo =this.editPersonalInfo
           this@HomeMainFragment.editProfession = this.editProfession
            this@HomeMainFragment.editYourself =this.editYourself
        }
        editPersonalInfo.setOnClickListener {
            navController.navigate(R.id.action_homeMainFragment_to_personalInfoFragment)
        }
        editProfession.setOnClickListener {
            navController.navigate(R.id.action_homeMainFragment_to_professionFragment)
        }
        editAddress.setOnClickListener {
            navController.navigate(R.id.action_homeMainFragment_to_addressFragment)
        }
        editYourself.setOnClickListener {
            navController.navigate(R.id.action_homeMainFragment_to_selfFragment)
        }
    }
}