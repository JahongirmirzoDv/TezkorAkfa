package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models

data class Profil(
    val id: Int,
    val purchase: List<Purchase>,
    val purchase_count: Int
)