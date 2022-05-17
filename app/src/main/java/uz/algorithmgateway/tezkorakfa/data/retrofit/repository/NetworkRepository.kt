package uz.algorithmgateway.tezkorakfa.data.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import uz.algorithmgateway.tezkorakfa.data.models.UserRequest
import uz.algorithmgateway.tezkorakfa.data.models.UserResponse
import uz.algorithmgateway.tezkorakfa.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.Responce
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory.Accessory
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile.Profile
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf.Shelf
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.window.Windows
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    fun salesOrderList(status: String, user_id: String): Flow<Result<OrderList>> {
        return apiService.salesOrderList(status, user_id)
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun getProfile(): Flow<Result<Profile>> {
        return apiService.getProfile()
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun getWindow(): Flow<Result<Windows>> {
        return apiService.getWindow()
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun getShelf(): Flow<Result<Shelf>> {
        return apiService.getShelf()
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun getAccsessory(): Flow<Result<Accessory>> {
        return apiService.getAccsessory()
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun loginUser(userRequest: UserRequest): Flow<Result<UserResponse>> {
        return apiService.loginUser(userRequest)
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    suspend fun acceptOrder(body: HashMap<String, Any>?) {
        return apiService.acceptOrder(body)
    }

    suspend fun sendData(
        id: String,
        body: RequestBody,
    ): Flow<Result<Responce>> {
        return apiService.sendData(id, body)
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }

    fun confirm(path:String,body: HashMap<String, Any>?): Flow<Result<Responce>> {
        return apiService.confirm(path,body)
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }
}