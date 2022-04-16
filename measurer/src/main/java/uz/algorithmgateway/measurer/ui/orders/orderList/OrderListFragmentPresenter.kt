package uz.algorithmgateway.measurer.ui.orders.orderList

import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import uz.algorithmgateway.data.api.service.ApiService
import java.lang.Exception

/**
 * Created by Abrorjon Berdiyorov on 11.03.2022
 */

@InjectViewState
class OrderListFragmentPresenter(private val mApiService: ApiService) :
    MvpPresenter<OrderListFragmentView>() {

    private var mSubscriptions = CompositeDisposable()
    private var orderStatus: String? = null

    fun setUp(status: String) {
        orderStatus = status
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.bindViews()
        getOrderList()
    }

    private fun getOrderList() {
        mSubscriptions.add(
            mApiService.getMeasurerOrderList(orderStatus!!)
                .doOnSubscribe { viewState.showProgress() }
                .doOnEvent { _, _ -> viewState.hideProgress() }
                .subscribe({
                    try {
                        viewState.updateOrdersList(it.results)
                    } catch (ex: Exception) {
                        viewState.showError(ex.message.toString())
                    }
                }, {
                    viewState.showError(it.message.toString())
                })
        )
    }


}