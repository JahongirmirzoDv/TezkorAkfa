package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

import uz.algorithmgateway.data.retrofit.models.sales_order_list.District
import uz.algorithmgateway.data.retrofit.models.sales_order_list.Scaler
import uz.algorithmgateway.data.retrofit.models.sales_order_list.ShowRoomX

data class Result(
    val address: String,
    val client: Client,
    val comment: String,
    val contract_number: String,
    val district: District,
    val id: Int,
    val measurement_time: String,
    val scaler: Scaler,
    val show_room: ShowRoomX,
    val status: String
)