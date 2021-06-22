package uz.texnopos.mybuilder.ui.builder

data class UserModel(
    var firstName: String ="",
    var lastName: String ="",
    var phone: String ="",
    var email: String ="",
    var builderCv:BuilderModel=BuilderModel()
)