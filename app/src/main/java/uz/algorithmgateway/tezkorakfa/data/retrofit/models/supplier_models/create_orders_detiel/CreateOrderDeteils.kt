package uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel

data class CreateOrderDeteils(
    val profil: Int,
    val place_of_origin: String,
    val count: Int,
    val price: Int
) {
}