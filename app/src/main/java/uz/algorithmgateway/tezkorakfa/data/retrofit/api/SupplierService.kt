package uz.algorithmgateway.tezkorakfa.data.retrofit.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList

interface SupplierService {

    @GET("order/order/")
    fun getOrdersList(): Flow<OrderList>

    @GET("android/order/supplier/detail/{contract_nuber}/")
    fun getOrderDetial(@Path("contract_nuber") contractNumber: String): Flow<OrderDetailsModel>

}