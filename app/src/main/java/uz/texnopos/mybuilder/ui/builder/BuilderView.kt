package uz.texnopos.mybuilder.ui.builder

interface BuilderView {
    fun showError(msg: String?)
    fun setData(builder: BuilderModel,msg:String)
    fun loading(isLoading: Boolean)
}