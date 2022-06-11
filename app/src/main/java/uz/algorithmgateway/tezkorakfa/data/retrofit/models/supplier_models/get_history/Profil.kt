package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history

data class Profil(
    val id: Int,
    val purchase: List<Purchase>,
    val purchase_price: Double
)