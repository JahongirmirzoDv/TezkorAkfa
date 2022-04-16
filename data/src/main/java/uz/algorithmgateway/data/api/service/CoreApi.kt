package uz.algorithmgateway.data.api.service

import io.reactivex.Single
import retrofit2.http.*
import uz.algorithmgateway.data.api.models.OrderResponse
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse

/**
 * Created by Abrorjon Berdiyorov on 11.03.2022
 */

interface CoreApi {

    @POST("user/login/")
    fun loginUser(@Body userRequest: UserRequest): Single<UserResponse>

    @GET("scaler/scaler/orders")
    fun getMeasurerOrderList(@Query("status") status: String): Single<OrderResponse>

}