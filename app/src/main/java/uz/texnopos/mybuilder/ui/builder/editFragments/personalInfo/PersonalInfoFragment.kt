package uz.texnopos.mybuilder.ui.builder.editFragments.personalInfo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentPersonalInfoBinding
import uz.texnopos.mybuilder.onClick
import uz.texnopos.mybuilder.textToString
import uz.texnopos.mybuilder.toast
import uz.texnopos.mybuilder.ui.builder.UserModel


class PersonalInfoFragment : Fragment(R.layout.fragment_personal_info), PersonalInfoView {
    lateinit var binding: FragmentPersonalInfoBinding
    private val presenter = PersonalInfoPresenter(this)
    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSave: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalInfoBinding.bind(view)
        etFirstName = binding.etFirstName
        etLastName = binding.etLastName
        etPhone = binding.etPhone
        etEmail = binding.etEmail
        btnSave = binding.btnSave
        presenter.getData()
        btnSave.onClick {
            val user = UserModel()
            user.firstName = etFirstName.textToString()
            user.lastName = etLastName.textToString()
            user.phone = etPhone.textToString()
            user.email = etEmail.textToString()
            presenter.setData(user)
        }

    }

    override fun userData(
        firstName: String,
        lastName: String,
        birthday: String,
        phone: String?,
        email: String?
    ) {
        etFirstName.setText(firstName)
        etLastName.setText(lastName)
        if (phone != null) etPhone.setText(phone)
        if (email != null) etEmail.setText(email)
    }

    override fun showMessage(message: String?) {
        toast(message!!)
    }

}