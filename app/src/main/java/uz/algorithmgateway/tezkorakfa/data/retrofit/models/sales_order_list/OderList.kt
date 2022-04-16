package uz.algorithmgateway.data.retrofit.models.sales_order_list

data class OderList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)