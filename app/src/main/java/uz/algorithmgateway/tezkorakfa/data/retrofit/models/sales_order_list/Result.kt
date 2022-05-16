package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class Result(
    val address: String,
    val all_price: AllPrice,
    val client: Client,
    val client_home_image: Any,
    val comment: String,
    val contract_number: String,
    val district: District,
    val id: Int,
    val measurement_time: String,
    val scaler: Scaler,
    val scaler_file: Any,
    val show_room: ShowRoom,
    val show_room_file: Any,
    val status: String,
    val total_price: String
)