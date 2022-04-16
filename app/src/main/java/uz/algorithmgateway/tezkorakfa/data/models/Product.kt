package uz.algorithmgateway.data.models

data class Product(
    val id:Long,
    val name:String,
    val type:Int,
    val count:Int,
    val price:Int,
    val total:Int,
    val isFound:Boolean
)
