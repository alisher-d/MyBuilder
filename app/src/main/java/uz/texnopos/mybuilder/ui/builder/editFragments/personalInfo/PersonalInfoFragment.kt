package uz.texnopos.mybuilder.ui.builder.editFragments.personalInfo

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import uz.texnopos.mybuilder.R
import uz.texnopos.mybuilder.databinding.FragmentPersonalInfoBinding
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
        btnSave.setOnClickListener {
            val user = UserModel()
            user.firstName = etFirstName.text.toString()
            user.lastName = etLastName.text.toString()
            user.phone = etPhone.text.toString()
            user.email = etEmail.text.toString()
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
        message.toast(context)
    }

}