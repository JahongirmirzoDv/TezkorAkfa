package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id

data class GetOrdersById(
    val contract_number: String,
    val id: Int,
    val profil: List<Profil>
)