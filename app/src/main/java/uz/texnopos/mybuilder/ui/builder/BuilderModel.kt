package uz.texnopos.mybuilder.ui.builder

import uz.texnopos.mybuilder.JobsModel

data class BuilderModel(
    var firstName: String ="",
    var lastName: String ="",
    var selectableModel: MutableList<JobsModel> = mutableListOf(),
    var nationality: String ="",
    var birthday: String ="",
    var phone: String ="",
    var email: String ="",
    var link: String ="",
    var description: String =""
)