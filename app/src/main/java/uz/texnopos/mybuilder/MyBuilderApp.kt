package uz.texnopos.mybuilder

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class MyBuilderApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        appInstance=this
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    companion object{
        private lateinit var appInstance: MyBuilderApp
        var sharedPrefUtils: SharedPrefUtils? = null

        fun getAppInstance(): MyBuilderApp {
            return appInstance
        }
    }

}