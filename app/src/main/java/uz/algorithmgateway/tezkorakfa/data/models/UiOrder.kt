package uz.algorithmgateway.tezkorakfa.data.models

import androidx.room.Entity

@Entity
data class UiOrder(
    val id:Int,
    val status:Int,
    val phone:String
)