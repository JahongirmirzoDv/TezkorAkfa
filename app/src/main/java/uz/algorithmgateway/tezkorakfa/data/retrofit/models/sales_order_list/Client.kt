package uz.algorithmgateway.data.retrofit.models.sales_order_list

data class Client(
    val address: String,
    val comment: String,
    val created_date: String,
    val district: Int,
    val email: Any,
    val first_name: String,
    val found: String,
    val id: Int,
    val last_name: String,
    val main_phone_number: String,
    val modified_date: String,
    val phone_number: String
)