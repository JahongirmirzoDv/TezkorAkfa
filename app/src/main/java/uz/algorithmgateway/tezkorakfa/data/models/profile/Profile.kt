package uz.algorithmgateway.tezkorakfa.data.models.profile

data class Profile(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)