package uz.algorithmgateway.tezkorakfa.base

import org.koin.dsl.module
import uz.algorithmgateway.measurer.ui.MeasurerPresenter
import uz.algorithmgateway.measurer.ui.orders.mainFragment.OrdersFragmentPresenter
import uz.algorithmgateway.measurer.ui.orders.orderList.OrderListFragmentPresenter
import uz.algorithmgateway.tezkorakfa.ui.launcher.LauncherPresenter
import uz.algorithmgateway.tezkorakfa.ui.login.LoginPresenter

val presentationModule = module {
    factory { LauncherPresenter(get()) }
    factory { LoginPresenter(get(), get()) }
    factory { MeasurerPresenter(get(), get()) }
    factory { OrdersFragmentPresenter() }
    factory { OrderListFragmentPresenter(get()) }
}