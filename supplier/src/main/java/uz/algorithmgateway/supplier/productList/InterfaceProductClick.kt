package uz.algorithmgateway.supplier.productList

import uz.algorithmgateway.data.models.Product

interface InterfaceProductClick {
    fun onButtonClick(product: Product)
}