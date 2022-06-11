package uz.algorithmgateway.tezkorakfa.data.retrofit.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_money.Create_MoneyReq
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrderDeteils
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrdersRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductById
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history.GetHistoryRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list.GetMoneyListRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Get_orders_list
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.GetOrdersById

interface SupplierService {

    @GET("order/order/")
    fun getOrdersList(): Flow<Get_orders_list>

    @GET("android/order/supplier/detail/{id}/")
    fun getOrderDetial(@Path("id") productId: String): Flow<GetOrdersById>

    @POST("android/order/supplier/create_purchase/")
    fun createOrderDetiel(@Body createOrderDeteils: CreateOrderDeteils): Flow<CreateOrdersRes>

    @GET("android/order/supplier/{id}/get_order/")
    fun getFoundProductById(@Path("id") productId: String): Flow<List<GetFoundProductByIdItem>>

    @GET("android/order/supplier/all_orders/")
    fun getHistory(): Flow<List<GetHistoryRes>>

    @GET("android/order/supplier/orders_sums/")
    fun getMoneyList(): Flow<List<GetMoneyListRes>>

    @POST("android/order/supplier/sum_create/")
    fun createMondey(@Body createMoneyreq: Create_MoneyReq): Flow<CreateOrdersRes>

}