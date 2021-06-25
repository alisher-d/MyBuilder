package uz.texnopos.mybuilder.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import uz.texnopos.mybuilder.*
import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.databinding.FragmentUsernameBinding
import uz.texnopos.mybuilder.models.BuilderModel
import uz.texnopos.mybuilder.models.UserModel

class UsernameFragment : BaseFragment(R.layout.fragment_username) {
    lateinit var bind: FragmentUsernameBinding
    private val currentUser = FirebaseAuth.getInstance().currentUser!!
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind = FragmentUsernameBinding.bind(view)
        bind.apply {
            val displayName = if (currentUser.displayName != null)
                currentUser.displayName
            else ""
            etFullName.setText(displayName)
            etPhone.setText(currentUser.phoneNumber ?: "")
            etEmail.setText(currentUser.email ?: "")
            navController = Navigation.findNavController(view)

            btnContinue.onClick {
                if (validate()) {
                    showProgress()
                    val user = UserModel()
                    user.fullName = etFullName.textToString()
                    user.phone = etPhone.textToString()
                    user.email = etEmail.textToString()
                    FirebaseHelper().setUserData(user,
                        {
                            hideProgress()
                            getSharedPreferences().setValue("succes", 1)
                            navController.navigate(R.id.action_navigation_username_to_navigation_profile)

                        }, {
                            hideProgress()
                            toast(it!!)
                        })
                }
            }
        }
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

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).navView.visibility=View.GONE
    }
}