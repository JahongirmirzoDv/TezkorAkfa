package uz.algorithmgateway.tezkorakfa.ui.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPref @Inject constructor(
    var context: Context
) {

    var isLogin: Boolean
        set(value) = mySharedPref.edit().putBoolean("device_token", value).apply()
        get() = mySharedPref.getBoolean("device_token", false)


    var endTime: String?
        get() = mySharedPref.getString("use1r", null)
        set(value) = mySharedPref.edit {
            if (value != null) {
                this.putString("use1r", value)
            }
        }

    private var mySharedPref: SharedPreferences =
        context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

}