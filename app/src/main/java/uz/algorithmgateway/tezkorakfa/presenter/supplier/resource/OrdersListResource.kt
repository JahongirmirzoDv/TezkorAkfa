package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.SupplierOrderlistItem

sealed class  OrdersListResource {

    object Loading : OrdersListResource()
    data class Error(val message: String) : OrdersListResource()
    data class SuccesList(val list: ArrayList<SupplierOrderlistItem>) : OrdersListResource()

}