package uz.algorithmgateway.tezkorakfa.data.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows

interface ApiService {
    @POST("user/login/")
    suspend fun loginUser(@Body userRequest: UserRequest): Response<UserResponse>

    @GET("order/order")
    suspend fun salesOrderList(
    ): Response<OderList>

    @GET("warehouse/profil/list")
    suspend fun getProfile(): Response<Profile>

    @GET("warehouse/profil/list")
    suspend fun getWindow(): Response<Windows>
}