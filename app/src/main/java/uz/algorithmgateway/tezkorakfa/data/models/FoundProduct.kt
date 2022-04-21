package uz.algorithmgateway.tezkorakfa.data.models

data class FoundProduct(
    val id:Int,
    val name:String,
    val type: Int,
    val count:Int,
    val price:String,
    val countByDealer:Int,
    val priceByDealer:String,
    val countByOutside: Int,
    val priceByOutside: String
)
