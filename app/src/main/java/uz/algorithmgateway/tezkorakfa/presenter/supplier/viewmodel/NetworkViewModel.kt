package uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrderDeteils
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Result
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.Profil
import uz.algorithmgateway.tezkorakfa.data.retrofit.repository.SupplierRepository
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.OrderDetailListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.OrdersListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.ProductFoundResource
import javax.inject.Inject

class NetworkViewModel @Inject constructor(val supplierRepository: SupplierRepository) :
    ViewModel() {

    fun getOrdersList(): StateFlow<OrdersListResource> {
        val flow = MutableStateFlow<OrdersListResource>(OrdersListResource.Loading)

        viewModelScope.launch {
            supplierRepository.getOrdersList().collect {
                if (it.isSuccess) {
                    flow.emit(OrdersListResource.SuccesList(it.getOrThrow().results as ArrayList<Result>))
                } else if (it.isFailure) flow.emit(OrdersListResource.Error(it.exceptionOrNull()?.message.toString()))
            }

        }
        return flow
    }

    fun getOrderDetialList(contractsNumber: String): StateFlow<OrderDetailListResource> {
        val flow = MutableStateFlow<OrderDetailListResource>(OrderDetailListResource.Loading)

        viewModelScope.launch {
            supplierRepository.getOrderDetialList(contractsNumber).collect {
                if (it.isSuccess) {
                    flow.emit(OrderDetailListResource.SuccesListBYId(it.getOrThrow()))
                } else if (it.isFailure) flow.emit(OrderDetailListResource.Error(it.exceptionOrNull()?.message.toString()))
            }

        }
        return flow
    }

    fun createOrdersDeteils(createOrderDeteils: CreateOrderDeteils): StateFlow<OrderDetailListResource> {
        val flow = MutableStateFlow<OrderDetailListResource>(OrderDetailListResource.Loading)

        viewModelScope.launch {
            supplierRepository.createOrdersDeteils(createOrderDeteils).collect {
                if (it.isSuccess) {
                    flow.emit(OrderDetailListResource.SuccesOrderDetils(true))
                } else if (it.isFailure) {
                    flow.emit(OrderDetailListResource.Error(it.exceptionOrNull()?.message.toString()))
                }
            }
        }
        return flow
    }

    fun getProductFoundData(productId: String): StateFlow<ProductFoundResource> {
        val flow = MutableStateFlow<ProductFoundResource>(ProductFoundResource.Loading)

        viewModelScope.launch {
            supplierRepository.getFoundProductId(productId).collect {
                if (it.isSuccess) {
                    flow.emit(ProductFoundResource.SuccesList(it.getOrThrow()))
                } else if (it.isFailure) {
                    flow.emit(ProductFoundResource.Error(it.exceptionOrNull()?.message.toString()))
                }
            }
        }

        return flow
    }

}