package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders

data class Result(
    val address: String,
    val client: Client,
    val client_home_image: Any,
    val comment: String,
    val contract_number: String,
    val district: DistrictX,
    val id: Int,
    val measurement_time: String,
    val paid_money: Any,
    val payment_must_be_made: Double,
    val scaler: Any,
    val scaler_file: Any,
    val seller: Any,
    val show_room: Any,
    val show_room_file: Any,
    val status: String,
    val total_price: Any
)