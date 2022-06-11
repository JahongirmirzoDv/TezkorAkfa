package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list

data class GetMoneyListRes(
    val contract_number: String,
    val id: Int,
    val measurement_time: String,
    val order_supplier: List<Any>,
    val profil: List<Profil>,
    val purchase_all_price: String,
    val supplier_send_price_all: Int
)