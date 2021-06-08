package uz.texnopos.mybuilder.ui.builder

data class BuilderModel(
    var selectableModel: MutableList<JobsModel> = mutableListOf(),
    var link: String ="",
    var description: String =""
)