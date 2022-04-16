package uz.algorithmgateway.data.retrofit.models.sales_order_list

import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result

data class OderList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)