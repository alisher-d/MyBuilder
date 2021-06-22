package uz.texnopos.mybuilder.ui.builder.editFragments.personalInfo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.databinding.FragmentPersonalInfoBinding
import uz.texnopos.mybuilder.models.UserModel


class PersonalInfoFragment : BaseFragment(R.layout.fragment_personal_info), PersonalInfoView {
    lateinit var binding: FragmentPersonalInfoBinding
    private val presenter = PersonalInfoPresenter(this)
    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInfoBinding.bind(view)
        navController = Navigation.findNavController(view)
        etFirstName = binding.etFirstName
        etLastName = binding.etLastName
        etPhone = binding.etPhone
        etEmail = binding.etEmail
        btnSave = binding.btnSave
        etFirstName.setText(getFirstName())
        etLastName.setText(getLastName())
        if (getPhoneNumber() != "") etPhone.setText(getPhoneNumber())
        if (getEmail() != "") etEmail.setText(getEmail())
        btnSave.onClick {
            showProgress()
            if (validate()) {
                showProgress()
                val user = UserModel()
                user.firstName = etFirstName.textToString()
                user.lastName = etLastName.textToString()
                user.phone = etPhone.textToString()
                user.email = etEmail.textToString()
                presenter.setData(user){
                    hideProgress()
                    navController.navigate(R.id.action_personalInfoFragment_to_homeMainFragment)
                }

            }
        }

    }
    override fun showMessage(message: String?) {
        toast(message!!)
    }

    private fun validate(): Boolean {
        return when {
            etFirstName.checkIsEmoty() -> {
                etFirstName.showError("Field Required")
                false
            }
            etLastName.checkIsEmoty() -> {
                etLastName.showError("Field Required")
                false
            }
            etPhone.checkIsEmoty() -> {
                etPhone.showError("Field Required")
                false
            }
            etEmail.checkIsEmoty() -> {
                etEmail.showError("Field Required")
                false
            }
            else -> true

        }
    }
}