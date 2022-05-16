package uz.algorithmgateway.tezkorakfa.data.retrofit

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import uz.algorithmgateway.tezkorakfa.data.models.UserRequest
import uz.algorithmgateway.tezkorakfa.data.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.Responce
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory.Accessory
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf.Shelf
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows

interface ApiService {
    @POST("user/login/")
    fun loginUser(@Body userRequest: UserRequest): Flow<UserResponse>

    @GET("order/order/")
    fun salesOrderList(
        @Query("position") status: String,
        @Query("scaler") user_id: String,
    ): Flow<OrderList>

    @GET("warehouse/profil/list")
    fun getProfile(): Flow<Profile>

    @GET("warehouse/window/list")
    fun getWindow(): Flow<Windows>

    @GET("warehouse/podogonnik/list")
    fun getShelf(): Flow<Shelf>

    @GET("warehouse/accessory/list")
    fun getAccsessory(): Flow<Accessory>

    @POST("order/order_update/android/")
    suspend fun updateUserData(
        @Body body: RequestBody,
    ): Response<ResponseBody>

    @POST("order/order/on_scaler/")
    suspend fun acceptOrder(
        @Body body: HashMap<String, Any>?,
    )

    @PATCH("order/order/update/{id}/")
    suspend fun sendData(
        @Path("id") id: String,
        @Body body: RequestBody,
    )

    @POST("order/order/{path}/")
    fun confirm(
        @Path("path") path: String,
        @Body body: HashMap<String, Any>?
    ):Flow<Responce>
}