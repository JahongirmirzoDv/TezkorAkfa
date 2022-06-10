package uz.algorithmgateway.tezkorakfa.data.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.SupplierService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import javax.inject.Inject

class SupplierRepository @Inject constructor(private val supplierService: SupplierService) {

    fun getOrdersList(): Flow<Result<OrderList>> {
        return supplierService.getOrdersList().map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun getOrderDetialList(contractsNumber: String): Flow<Result<OrderDetailsModel>> {
        return supplierService.getOrderDetial(contractsNumber).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }
}