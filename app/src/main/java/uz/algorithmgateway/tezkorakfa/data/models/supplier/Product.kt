package uz.algorithmgateway.tezkorakfa.data.models.supplier

data class Product(
    val color: String,
    val id: Int,
    val items: List<Item>,
    val length: Double,
    val measurement_type: String,
    val price: String,
    val sub_title: String,
    val title: String,
    val type: Type
)