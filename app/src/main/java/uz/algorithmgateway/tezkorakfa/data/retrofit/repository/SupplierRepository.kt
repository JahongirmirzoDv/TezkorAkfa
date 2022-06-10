package uz.algorithmgateway.tezkorakfa.data.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.SupplierService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrderDeteils
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrdersRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductById
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Get_orders_list
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.GetOrdersById
import javax.inject.Inject

class SupplierRepository @Inject constructor(private val supplierService: SupplierService) {

    fun getOrdersList(): Flow<Result<Get_orders_list>> {
        return supplierService.getOrdersList().map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun getOrderDetialList(contractsNumber: String): Flow<Result<GetOrdersById>> {
        return supplierService.getOrderDetial(contractsNumber).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun createOrdersDeteils(createOrderDeteils: CreateOrderDeteils): Flow<Result<CreateOrdersRes>> {
        return supplierService.createOrderDetiel(createOrderDeteils).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun getFoundProductId(productId: String): Flow<Result<GetFoundProductById>> {
        return supplierService.getFoundProductById(productId).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }
}