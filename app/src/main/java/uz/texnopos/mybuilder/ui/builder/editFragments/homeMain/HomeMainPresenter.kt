package uz.texnopos.mybuilder.ui.builder.editFragments.homeMain

import uz.texnopos.mybuilder.data.FirebaseHelper

class HomeMainPresenter(val view: HomeMainView) {
    private val dbHelper = FirebaseHelper()
    fun getUserData() {
        dbHelper.getUserData(
            {
                view.userData(it.firstName, it.lastName, it.birthday, it.phone, it.email,it.builderCv)
            },
            {
                view.showMessage(it)
            }
        )
    }

}