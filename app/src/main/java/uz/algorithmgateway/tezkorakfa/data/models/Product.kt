package uz.algorithmgateway.tezkorakfa.data.models

data class Product(
    val id:Long,
    val name:String,
    val type:Int,
    val count:Int,
    val price:Int,
    val total:Int,
    val isFound:Boolean
)
