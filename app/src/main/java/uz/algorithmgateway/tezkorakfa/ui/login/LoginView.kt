package uz.algorithmgateway.tezkorakfa.ui.login

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution


/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

interface LoginView : MvpView {
    @OneExecution
    fun finishView()

    @OneExecution
    fun showToast(string: String?)

    @OneExecution
    fun showNetworkError()

    @OneExecution
    fun hideProgress()

    @OneExecution
    fun showProgress()

    @OneExecution
    fun navigateToMeasure()

    @OneExecution
    fun showError(error: String?)

    @OneExecution
    fun navigateToSupplier()

    @OneExecution
    fun navigateToMontage()

    @OneExecution
    fun navigateToService()
}
