package uz.algorithmgateway.tezkorakfa.presenter.supplier.resource

import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductById
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history.GetHistoryRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list.GetMoneyListRes
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.Profil

sealed class ProductFoundResource {

    object Loading : ProductFoundResource()
    data class Error(val message: String) : ProductFoundResource()
    data class SuccesList(val data: ArrayList<GetFoundProductByIdItem>) : ProductFoundResource()
    data class SuccesListHistory(val data: ArrayList<GetHistoryRes>) : ProductFoundResource()
    data class SuccesListMoney(val data: ArrayList<GetMoneyListRes>) : ProductFoundResource()
    data class Succes(val success: String) : ProductFoundResource()

}