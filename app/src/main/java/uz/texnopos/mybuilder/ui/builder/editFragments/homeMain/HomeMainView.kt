package uz.texnopos.mybuilder.ui.builder.editFragments.homeMain

import uz.texnopos.mybuilder.ui.builder.BuilderModel

interface HomeMainView {
    fun userData(firstName:String,lastName:String,birthday:String,phone:String?,email:String?,builderCv:BuilderModel?)
    fun showMessage(message: String?)
}
