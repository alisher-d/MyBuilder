package uz.texnopos.mybuilder.models


data class BuilderModel(
    var personal: UserModel=UserModel(),
    var userUID:String="",
    var created: Boolean = false,
    var published:Boolean=true,
    var selectableJobs:ArrayList<String> = arrayListOf(),
    var profession:String="",
    var address:Address=Address(),
    var link: String = "",
    var description: String = ""
){
    data class Address(
        var lat:Double=0.0,
        var long:Double=0.0,
        var cityName:String?="",
        var countryName:String?="",
        var stateName:String?=""
    )
}
