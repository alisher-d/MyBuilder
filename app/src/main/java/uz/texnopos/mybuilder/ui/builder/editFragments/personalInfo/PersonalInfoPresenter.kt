package uz.texnopos.mybuilder.ui.builder.editFragments.personalInfo

import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.ui.builder.UserModel

class PersonalInfoPresenter(val view: PersonalInfoView) {
    private val dbHelper=FirebaseHelper()
    fun getData(){
        dbHelper.getUserData(
            {
                view.userData(it.firstName,it.lastName,it.birthday,it.phone,it.email)
            },
            {
                view.showMessage(it)
            }
        )
    }
    fun setData(userModel: UserModel){
        dbHelper.setUserData(userModel,
            {
                view.showMessage(it)
            },
            {
                view.showMessage(it)
            })
    }
}