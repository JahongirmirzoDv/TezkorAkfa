package uz.algorithmgateway.supplier.orderList

import uz.algorithmgateway.data.models.OrderSupplier

interface InterfaceOrderClick {
    fun onItemClick(order: OrderSupplier)
}