package uz.algorithmgateway.supplier.orderList

import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier

interface InterfaceOrderClick {
    fun onItemClick(order: OrderSupplier)
}