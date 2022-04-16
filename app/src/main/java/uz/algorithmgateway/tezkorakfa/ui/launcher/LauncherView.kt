package uz.algorithmgateway.tezkorakfa.ui.launcher

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

interface LauncherView : MvpView {

    @OneExecution
    fun navigateToSplash()

    @OneExecution
    fun navigateToLogin()

    @OneExecution
    fun navigateToMeasurer()

    @OneExecution
    fun finishView()

    @OneExecution
    fun showToast(string: String?)

}