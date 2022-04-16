package uz.algorithmgateway.measurer.ui.orders.orderList

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import uz.algorithmgateway.data.api.models.Order


/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

interface OrderListFragmentView:MvpView {

    @AddToEndSingle
    fun bindViews()

    @AddToEndSingle
    fun updateOrdersList(orderList: List<Order>)

    @AddToEndSingle
    fun showError(error: String)

    @AddToEndSingle
    fun showProgress()

    @AddToEndSingle
    fun hideProgress()
}