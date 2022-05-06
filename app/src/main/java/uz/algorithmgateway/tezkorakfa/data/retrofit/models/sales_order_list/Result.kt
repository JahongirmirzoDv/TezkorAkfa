package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class Result(
    val address: String,
    val all_price: AllPrice,
    val client: Client,
    val comment: String,
    val contract_number: String,
    val district: District,
    val id: Int,
    val measurement_time: String,
    val scaler: Any,
    val scaler_file: String,
    val show_room: ShowRoom,
    val status: String,
    val total_price: String
)