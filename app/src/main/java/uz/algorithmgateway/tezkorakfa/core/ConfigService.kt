package uz.algorithmgateway.core

import android.content.Context
import android.content.SharedPreferences
import uz.algorithmgateway.data.const.Constants
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Abrorjon Berdiyorov on 04.03.2022
 */

@Singleton
interface ConfigService   {

    companion object {
        const val CONFIG_SERVICE_TAG = "appConfigService"
    }

    fun setAccessToken(token: String?)

    fun getAccessToken(): String

    fun setUserId(userId: Int)

    fun getUserId(): Int

    fun setUserRole(roleId: String)

    fun getUserRole(): String
}


class ConfigServiceImpl (context: Context, configFileName: String) : ConfigService {


    private val mSharedPreferences: SharedPreferences =
        context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)


    override fun setAccessToken(token: String?) {
        mSharedPreferences.edit().putString(Constants.PREF_ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken(): String {
        return mSharedPreferences.getString(Constants.PREF_ACCESS_TOKEN, "")!!
    }

    override fun setUserId(userId: Int) {
        mSharedPreferences.edit().putInt(Constants.PREF_USER_ID, userId).apply()
    }

    override fun getUserId(): Int {
        return mSharedPreferences.getInt(Constants.PREF_USER_ID, 0)
    }

    override fun setUserRole(roleId: String) {
        mSharedPreferences.edit().putString(Constants.PREF_USER_ROLE, roleId).apply()
    }

    override fun getUserRole(): String {
        return mSharedPreferences.getString(Constants.PREF_USER_ROLE, "")!!
    }
}