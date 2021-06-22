package uz.texnopos.mybuilder.ui.builder

data class BuilderModel(
    var created: Boolean = false,
    var selectableModel: ArrayList<String> = arrayListOf(),
    var profession:String="",
    var link: String = "",
    var description: String = ""
)