package uz.algorithmgateway.tezkorakfa.data.retrofit.models.shelf

data class Shelf(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)