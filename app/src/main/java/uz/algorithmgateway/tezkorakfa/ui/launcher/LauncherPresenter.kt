package uz.algorithmgateway.tezkorakfa.ui.launcher

import android.content.Intent
import moxy.InjectViewState
import moxy.MvpPresenter
import uz.algorithmgateway.core.ConfigService

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

@InjectViewState
class LauncherPresenter(
    private val configService: ConfigService
) : MvpPresenter<LauncherView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.navigateToSplash()
    }

    fun onSplashResult(isResultOk: Boolean, data: Intent?) {
        if (isResultOk) {
            val string = configService.getAccessToken()

            if (string == "") {
                viewState.navigateToLogin()
            } else {
                viewState.navigateToMeasurer()
            }
        }
        viewState.finishView()
    }

}