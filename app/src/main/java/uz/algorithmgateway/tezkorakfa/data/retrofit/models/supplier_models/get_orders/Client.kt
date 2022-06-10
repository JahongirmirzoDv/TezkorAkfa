package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders

data class Client(
    val address: String,
    val comment: Any,
    val district: District,
    val first_name: String,
    val found: String,
    val id: Int,
    val last_name: String,
    val main_phone_number: String,
    val phone_number: String
)