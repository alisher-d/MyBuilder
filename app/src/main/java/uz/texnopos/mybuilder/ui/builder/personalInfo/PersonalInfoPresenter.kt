package uz.texnopos.mybuilder.ui.builder.personalInfo

import uz.texnopos.mybuilder.data.FirebaseHelper
import uz.texnopos.mybuilder.models.UserModel

class PersonalInfoPresenter(val view: PersonalInfoView) {
    private val dbHelper=FirebaseHelper()
    fun setData(userModel: UserModel,
    onSucces:(msg:String)->Unit){
        dbHelper.updatePersonalData(userModel,
            {
                onSucces.invoke(it!!)
                view.showMessage(it)
            },
            {
                view.showMessage(it)
            })
    }
}