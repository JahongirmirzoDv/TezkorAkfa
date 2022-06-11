package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_history

data class GetHistoryRes(
    val contract_number: String,
    val degrees: String,
    val id: Int,
    val profil: List<Profil>,
    val purchase_all_price: Double
)