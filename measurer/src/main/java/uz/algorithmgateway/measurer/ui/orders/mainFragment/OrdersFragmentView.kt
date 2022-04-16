package uz.algorithmgateway.measurer.ui.orders.mainFragment

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

interface OrdersFragmentView : MvpView {

    @OneExecution
    fun showToast()

}