package uz.algorithmgateway.tezkorakfa.data.retrofit.models.profile

data class Result(
    val id: Int,
    val name: String,
    val type: List<Type>
)