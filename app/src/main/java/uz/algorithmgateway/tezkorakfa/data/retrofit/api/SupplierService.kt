package uz.algorithmgateway.tezkorakfa.data.retrofit.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList

interface SupplierService {

    @GET("order/order/")
    fun getOrdersList(): Flow<OrderList>

}