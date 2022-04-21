package uz.algorithmgateway.supplier.productList

import uz.algorithmgateway.tezkorakfa.data.models.Product

interface InterfaceProductClick {
    fun onButtonClick(product: Product)
}