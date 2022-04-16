package uz.algorithmgateway.data.api.service

import io.reactivex.Single
import uz.algorithmgateway.data.api.models.OrderResponse
import uz.algorithmgateway.data.api.models.UserResponse

/**
 * Created by Abrorjon Berdiyorov on 04.03.2022
 */

interface ApiService {


    fun isNetworkConnected(): Boolean

    fun loginUser(
        phone: String,
        password: String
    ): Single<UserResponse>

    fun getMeasurerOrderList(status:String): Single<OrderResponse>

}