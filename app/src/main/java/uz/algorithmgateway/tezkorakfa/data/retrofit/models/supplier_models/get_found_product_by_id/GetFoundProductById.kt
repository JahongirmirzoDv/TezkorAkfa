package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id

data class GetFoundProductById(
    val contract_number: String,
    val id: Int,
    val profil: List<Profil>,
    val total_price_diller: Double,
    val total_price_street: Double
)