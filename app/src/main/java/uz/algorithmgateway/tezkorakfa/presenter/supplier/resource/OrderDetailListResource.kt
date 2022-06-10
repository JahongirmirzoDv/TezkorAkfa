package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList

sealed class OrderDetailListResource {

    object Loading : OrderDetailListResource()
    data class Error(val message: String) : OrderDetailListResource()
    data class SuccesList(val list: OrderDetailsModel) : OrderDetailListResource()

}