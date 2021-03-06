package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Get_orders_list
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Result
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.GetOrdersById

sealed class OrderDetailListResource {

    object Loading : OrderDetailListResource()
    data class Error(val message: String) : OrderDetailListResource()
    data class SuccesList(val list: OrderDetailsModel) : OrderDetailListResource()
    data class SuccesOrderDetils(val deteils: Boolean) : OrderDetailListResource()
    data class SuccesListBYId(val list: GetOrdersById) : OrderDetailListResource()

}