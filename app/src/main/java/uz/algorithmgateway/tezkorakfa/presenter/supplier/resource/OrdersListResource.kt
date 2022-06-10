package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Get_orders_list
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Result

sealed class  OrdersListResource {

    object Loading : OrdersListResource()
    data class Error(val message: String) : OrdersListResource()
    data class SuccesList(val list: ArrayList<Result>) : OrdersListResource()

}