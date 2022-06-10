package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id

data class Profil(
    val articul: String,
    val color: String,
    val count: Double,
    val id: Int,
    val price: Int,
    val purchase: List<Purchase>,
    val title: String,
    val total_price_diller_purchase: Double,
    val total_price_street_purchase: Double
)