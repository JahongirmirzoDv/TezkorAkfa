package uz.algorithmgateway.tezkorakfa.di.component

import dagger.Component
import uz.algorithmgateway.tezkorakfa.di.module.NetworkModule
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList.OrderListFragment
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun login(loginActivity: LoginActivity)
    fun orders(orderListFragment: OrderListFragment)
}