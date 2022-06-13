package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models

data class SupplierOrderlistItem(
    val contract_number: String,
    val degrees: String,
    val id: Int,
    val profil: List<Profil>,
    val purchase_all_count: Int,
    val purchase_count: Int,
    val total_price: String
)