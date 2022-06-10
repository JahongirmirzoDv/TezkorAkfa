package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.models.supplier.OrderDetailsModel
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.OrderList
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductById
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.Profil
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Get_orders_list
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders.Result
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.GetOrdersById

sealed class ProductFoundResource {

    object Loading : ProductFoundResource()
    data class Error(val message: String) : ProductFoundResource()
    data class SuccesList(val data: ArrayList<Profil>) : ProductFoundResource()

}