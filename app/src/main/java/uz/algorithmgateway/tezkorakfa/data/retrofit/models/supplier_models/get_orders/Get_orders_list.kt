package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders

data class Get_orders_list(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)