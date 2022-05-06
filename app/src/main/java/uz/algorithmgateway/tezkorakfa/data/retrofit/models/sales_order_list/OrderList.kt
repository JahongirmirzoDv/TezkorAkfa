package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class OrderList(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)