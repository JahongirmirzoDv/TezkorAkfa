package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

import uz.algorithmgateway.data.retrofit.models.sales_order_list.DistrictXX
import java.io.Serializable

data class ShowRoomX(
    val district: DistrictXX,
    val id: Int,
    val name: String
):Serializable