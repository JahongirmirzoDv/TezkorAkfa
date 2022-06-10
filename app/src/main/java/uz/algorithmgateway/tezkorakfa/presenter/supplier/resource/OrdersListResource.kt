package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList

sealed class  OrdersListResource {

    object Loading : OrdersListResource()
    data class Error(val message: String) : OrdersListResource()
    data class SuccesList(val list: OrderList) : OrdersListResource()

}