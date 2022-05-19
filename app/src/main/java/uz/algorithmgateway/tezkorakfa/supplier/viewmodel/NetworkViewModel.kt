package uz.algorithmgateway.tezkorakfa.supplier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.data.retrofit.repository.SupplierRepository
import uz.algorithmgateway.tezkorakfa.measurer.resource.OrdersListResource
import javax.inject.Inject

class NetworkViewModel @Inject constructor(val supplierRepository: SupplierRepository) :
    ViewModel() {

    fun getOrdersList(): StateFlow<OrdersListResource> {
        val flow = MutableStateFlow<OrdersListResource>(OrdersListResource.Loading)

        viewModelScope.launch {
            supplierRepository.getOrdersList().collect {
                if (it.isSuccess) {
                    flow.emit(OrdersListResource.SuccesList(it.getOrThrow()))
                } else if (it.isFailure) flow.emit(OrdersListResource.Error(it.exceptionOrNull()?.message.toString()))
            }

        }
        return flow

    }

}