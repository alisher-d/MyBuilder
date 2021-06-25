package uz.texnopos.mybuilder.ui.builder.personalInfo

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.databinding.FragmentPersonalInfoBinding
import uz.texnopos.mybuilder.models.UserModel


class PersonalInfoFragment : BaseFragment(R.layout.fragment_personal_info), PersonalInfoView {
    lateinit var bind: FragmentPersonalInfoBinding
    private val presenter = PersonalInfoPresenter(this)
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentPersonalInfoBinding.bind(view)
        navController = Navigation.findNavController(view)
        bind.apply {

            etFullName.setText(getFullName())
            if (getPhoneNumber() != "") etPhone.setText(getPhoneNumber())
            if (getEmail() != "") etEmail.setText(getEmail())
            btnSave.onClick {
                if (validate()) {
                    showProgress()
                    val user = UserModel()
                    user.fullName = etFullName.textToString()

                    user.phone = etPhone.textToString()
                    user.email = etEmail.textToString()
                    presenter.setData(user) {
                        hideProgress()
                        navController.navigate(R.id.action_personalInfoFragment_to_homeMainFragment)
                    }

                }
            }
        }
    }
    override fun showMessage(message: String?) {
        toast(message!!)
    }

    private fun validate(): Boolean {
        return when {
            bind.etFullName.checkIsEmpty() -> {
                bind.etFullName.showError("Field Required")
                false
            }
            bind.etPhone.checkIsEmpty() -> {
                bind.etPhone.showError("Field Required")
                false
            }
            bind.etEmail.checkIsEmpty() -> {
                bind.etEmail.showError("Field Required")
                false
            }
            else -> true

        }
    }
}