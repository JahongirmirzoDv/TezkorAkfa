package uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list

data class AllPrice(
    val paid_prices: Double,
    val payment_must_be_made: Double,
    val total_price: Double
)