package uz.algorithmgateway.tezkorakfa.data.retrofit.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import uz.algorithmgateway.tezkorakfa.data.retrofit.api.SupplierService
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.SupplierOrderlistItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_money.Create_MoneyReq
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrderDeteils
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrdersRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history.GetHistoryRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list.GetMoneyListRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.GetOrdersById
import javax.inject.Inject

class SupplierRepository @Inject constructor(private val supplierService: SupplierService) {

    fun getOrdersList(): Flow<Result<ArrayList<SupplierOrderlistItem>>> {
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

    fun getFoundProductId(productId: String): Flow<Result<List<GetFoundProductByIdItem>>> {
        return supplierService.getFoundProductById(productId).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun getHistoryOrders(): Flow<Result<List<GetHistoryRes>>> {
        return supplierService.getHistory().map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun getMoneyList(): Flow<Result<List<GetMoneyListRes>>> {
        return supplierService.getMoneyList().map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

    fun createMoney(createMoneyreq: Create_MoneyReq): Flow<Result<CreateOrdersRes>> {
        return supplierService.createMondey(createMoneyreq).map {
            Result.success(it)
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
    }

}