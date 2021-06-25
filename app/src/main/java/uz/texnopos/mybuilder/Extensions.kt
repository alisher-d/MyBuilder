package uz.texnopos.mybuilder

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import uz.texnopos.mybuilder.Constants.SharedPref.ADDRESS
import uz.texnopos.mybuilder.Constants.SharedPref.CREATED
import uz.texnopos.mybuilder.Constants.SharedPref.DESCRIPTION
import uz.texnopos.mybuilder.Constants.SharedPref.PROFESSION
import uz.texnopos.mybuilder.Constants.SharedPref.PUBLISH
import uz.texnopos.mybuilder.Constants.SharedPref.SELECTABLE_JOBS
import uz.texnopos.mybuilder.Constants.SharedPref.USER_EMAIL
import uz.texnopos.mybuilder.Constants.SharedPref.USER_FULL_NAME
import uz.texnopos.mybuilder.Constants.SharedPref.USER_PHONE_NUMBER
import uz.texnopos.mybuilder.MyBuilderApp.Companion.getAppInstance

fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_LONG) {
    if (context != null) {
        context!!.toast(text, duration)
    }
}

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }

fun TextInputEditText.textToString() = this.text.toString()


fun getSharedPreferences(): SharedPrefUtils {
    return if (MyBuilderApp.sharedPrefUtils == null) {
        MyBuilderApp.sharedPrefUtils = SharedPrefUtils()
        MyBuilderApp.sharedPrefUtils!!
    } else MyBuilderApp.sharedPrefUtils!!
}

fun Fragment.isGPSEnable(): Boolean =
    context!!.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)

fun Context.getLocationManager() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun View.showSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun TextInputEditText.checkIsEmpty(): Boolean = text == null ||
        textToString() == "" ||
        textToString().equals("null", ignoreCase = true)

fun TextInputEditText.showError(error: String) {
    this.error = error
    this.showSoftKeyboard()
}

fun getFullName(): String = getSharedPreferences().getStringValue(USER_FULL_NAME)
fun getPhoneNumber(): String = getSharedPreferences().getStringValue(USER_PHONE_NUMBER)
fun getEmail(): String = getSharedPreferences().getStringValue(USER_EMAIL)
fun getCreated(): Boolean = getDescription().isNotEmpty() || getSelectableJobs().isNotEmpty() ||
        getAddress().isNotEmpty()

fun getPublished(): Boolean = getSharedPreferences().getBooleanValue(PUBLISH)
fun getDescription(): String = getSharedPreferences().getStringValue(DESCRIPTION)
fun getProfession(): String = getSharedPreferences().getStringValue(PROFESSION)
fun getAddress(): String = getSharedPreferences().getStringValue(ADDRESS)
fun getSelectableJobs(): ArrayList<String> =
    getSharedPreferences().getStringSetValue(SELECTABLE_JOBS)!!.toCollection(ArrayList())

fun clearLoginPref() {
    getSharedPreferences().removeKey(USER_FULL_NAME)
    getSharedPreferences().removeKey(USER_PHONE_NUMBER)
    getSharedPreferences().removeKey(CREATED)
    getSharedPreferences().removeKey(PUBLISH)
    getSharedPreferences().removeKey(PROFESSION)
    getSharedPreferences().removeKey(DESCRIPTION)
    getSharedPreferences().removeKey(ADDRESS)
    getSharedPreferences().removeKey(SELECTABLE_JOBS)
}

@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
fun isNetworkAvailable(): Boolean {
    val info = getAppInstance().getConnectivityManager().activeNetworkInfo
    return info != null && info.isConnected
}

fun Context.getConnectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
