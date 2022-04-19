package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

import uz.algorithmgateway.data.retrofit.models.sales_order_list.Role
import uz.algorithmgateway.data.retrofit.models.sales_order_list.ShowRoom
import java.io.Serializable

data class Scaler(
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone_number: String,
    val role: Role,
    val show_room: ShowRoom
):Serializable