package uz.texnopos.mybuilder.ui.builder.editFragments.homeMain

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentHomeMainBinding
import uz.texnopos.mybuilder.ui.builder.BuilderModel


class HomeMainFragment : Fragment(R.layout.fragment_home_main), HomeMainView {
    private val presenter = HomeMainPresenter(this)
    private lateinit var binding: FragmentHomeMainBinding
    private lateinit var editAddress: LinearLayout
    private lateinit var editPersonalInfo: LinearLayout
    private lateinit var editProfession: LinearLayout
    private lateinit var editYourself: LinearLayout
    private lateinit var tvUserName: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvEmail: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeMainBinding.bind(view)
        val navController = Navigation.findNavController(view)
        binding.apply {
            this@HomeMainFragment.editAddress = this.editAddress
            this@HomeMainFragment.editPersonalInfo = this.editPersonalInfo
            this@HomeMainFragment.editProfession = this.editProfession
            this@HomeMainFragment.editYourself = this.editYourself
        }
        tvUserName=binding.tvUserName
        tvPhone=binding.tvPhone
        tvEmail=binding.tvEmail
        presenter.getUserData()
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

    override fun userData(
        firstName: String,
        lastName: String,
        birthday: String,
        phone: String?,
        email: String?,
        builderCv: BuilderModel?
    ) {
        tvUserName.text = "$firstName $lastName"
        if (phone != null) tvPhone.text = phone
        else tvPhone.visibility=View.GONE
        if (email!=null) tvEmail.text = email
        else tvEmail.visibility=View.GONE
    }

    override fun showMessage(message: String?) {
        TODO("Not yet implemented")
    }
}