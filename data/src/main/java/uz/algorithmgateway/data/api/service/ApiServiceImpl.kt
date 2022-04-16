package uz.algorithmgateway.data.api.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.algorithmgateway.data.api.models.OrderResponse
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse

/**
 * Created by Abrorjon Berdiyorov on 11.03.2022
 */

class ApiServiceImpl(
    private val context: Context,
    private val mCoreApi: CoreApi
) : ApiService {

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun loginUser(phone: String, password: String): Single<UserResponse> {
        return mCoreApi.loginUser(UserRequest(phone, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMeasurerOrderList(status:String): Single<OrderResponse> {
        return mCoreApi.getMeasurerOrderList(status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}