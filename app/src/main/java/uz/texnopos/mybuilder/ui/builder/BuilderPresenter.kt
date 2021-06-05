package uz.texnopos.mybuilder.ui.builder

import uz.texnopos.mybuilder.data.FirebaseHelper

class BuilderPresenter(private val view: BuilderView) {
    private val dbHelper = FirebaseHelper()

    fun getBuilderData() {
        view.loading(true)
        dbHelper.getBuilderData(
            { it, msg ->
                view.loading(false)
                view.setData(it,msg)
            },
            {
                view.loading(false)
                view.showError(it)
            }
        )
    }
}