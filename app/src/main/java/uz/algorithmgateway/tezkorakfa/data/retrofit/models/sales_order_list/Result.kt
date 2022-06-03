package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class Result(
    val address: String,
    val client: Client,
    val client_home_image: String,
    val comment: String,
    val contract_number: String,
    val district: District,
    val id: Int,
    val measurement_time: String,
    val paid_money: String,
    val payment_must_be_made: Double,
    val scaler: Scaler,
    val scaler_file: String,
    val seller: Int,
    val show_room: ShowRoom,
    val show_room_file: String,
    val status: String,
    val total_price: String
)