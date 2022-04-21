package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

import java.io.Serializable

data class OderList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>,
):Serializable