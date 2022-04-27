package uz.algorithmgateway.tezkorakfa.data.retrofit.models.accessory

data class Result(
    val id: Int,
    val name: String,
    val type: List<Type>
)