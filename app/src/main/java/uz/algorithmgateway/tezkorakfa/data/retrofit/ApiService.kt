package uz.algorithmgateway.tezkorakfa.data.retrofit

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.algorithmgateway.data.api.models.UserRequest
import uz.algorithmgateway.data.api.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory.Accessory
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf.Shelf
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows

interface ApiService {
    @POST("user/login/")
    suspend fun loginUser(@Body userRequest: UserRequest): Response<UserResponse>

    @GET("order/order")
    fun salesOrderList(
    ): Flow<OderList>

    @GET("warehouse/profil/list")
    fun getProfile(): Flow<Profile>

    @GET("warehouse/window/list")
    fun getWindow(): Flow<Windows>

    @GET("warehouse/podogonnik/list")
    fun getShelf(): Flow<Shelf>

    @GET("warehouse/accessory/list")
    fun getAccsessory(): Flow<Accessory>


//    val files = File(filePath!!).compress2(requireContext())
//
//    val builder: MultipartBody.Builder = MultipartBody.Builder()
//    builder.setType(MultipartBody.FORM)
//    builder.addFormDataPart("id", orderId!!.toString())
//    builder.addFormDataPart(
//    "img",
//    files.name,
//    files.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//    )
//    val body = builder.build()
}