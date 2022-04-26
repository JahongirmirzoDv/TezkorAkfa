package uz.algorithmgateway.tezkorakfa.data.retrofit.models.window

data class Windows(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)