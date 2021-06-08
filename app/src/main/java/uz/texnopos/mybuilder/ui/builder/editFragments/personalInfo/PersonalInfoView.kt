package uz.texnopos.mybuilder.ui.builder.editFragments.personalInfo


interface PersonalInfoView {
    fun userData(firstName:String,lastName:String,birthday:String,phone:String?,email:String?)
    fun showMessage(message: String?)
}