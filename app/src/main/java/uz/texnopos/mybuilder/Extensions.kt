package uz.texnopos.mybuilder

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import uz.texnopos.mybuilder.Constants.SharedPref.IS_LOGGED_IN


fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    if (context != null) {
        context!!.toast(text, duration)
    }
}

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

fun TextInputEditText.textToString() = this.text.toString()

fun isLoggedIn():Boolean= getSharedPreferences().getBooleanValue(IS_LOGGED_IN)


fun getSharedPreferences(): SharedPrefUtils {
    return if (MyBuilderApp.sharedPrefUtils == null) {
        MyBuilderApp.sharedPrefUtils = SharedPrefUtils()
        MyBuilderApp.sharedPrefUtils!!
    }
    else MyBuilderApp.sharedPrefUtils!!
}

