package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class Client(
    val address: String,
    val comment: String,
    val district: District,
    val first_name: String,
    val found: String,
    val id: Int,
    val last_name: String,
    val main_phone_number: String,
    val phone_number: String
)