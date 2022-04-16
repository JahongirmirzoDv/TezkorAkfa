package uz.algorithmgateway.measurer.ui

import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView
import uz.algorithmgateway.core.ConfigService
import uz.algorithmgateway.data.api.service.ApiService

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

@InjectViewState
class MeasurerPresenter(
    private val configService: ConfigService,
    private val mApiService: ApiService
) : MvpPresenter<MeasurerView>() {


}