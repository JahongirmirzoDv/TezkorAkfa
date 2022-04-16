package uz.algorithmgateway.tezkorakfa.ui.login

import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import uz.algorithmgateway.core.ConfigService
import uz.algorithmgateway.data.api.service.ApiService
import uz.algorithmgateway.data.const.Value

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

@InjectViewState
class LoginPresenter(
    private val configService: ConfigService,
    private val mApiService: ApiService
) : MvpPresenter<LoginView>() {

    private var mSubscriptions = CompositeDisposable()


    fun onEnterClick(phone: String, password: String) {
        mSubscriptions.add(
            mApiService.loginUser(phone, password)
                .subscribe({
                    //view?.hideProgress()
                    configService.setAccessToken(it.token)
                    configService.setUserRole(it.roleId)
                    configService.setUserId(it.userId)
                    when (it.roleId) {
                        Value.SCALER -> {
                            viewState.navigateToMeasure()
                        }
                        Value.SUPPLIER -> {
                            viewState.navigateToSupplier()
                        }
                        Value.MONTAGER -> {
                            viewState.navigateToMontage()
                        }
                        Value.SERVICER -> {
                            viewState.navigateToService()
                        }
                    }

                }, {
                    viewState.hideProgress()
                    viewState.showError(it.message)
                })
        )
    }

}