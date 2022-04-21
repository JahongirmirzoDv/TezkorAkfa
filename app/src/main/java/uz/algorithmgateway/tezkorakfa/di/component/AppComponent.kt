package uz.algorithmgateway.tezkorakfa.di.component

import dagger.Component
import uz.algorithmgateway.tezkorakfa.di.module.ApplicationModule
import uz.algorithmgateway.tezkorakfa.di.module.DatabaseModule
import uz.algorithmgateway.tezkorakfa.di.module.NetworkModule
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.DrawingsFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList.OrderListFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.OrderSelectTypeScreen
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.ui.slider_standart.SliderScreen
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ApplicationModule::class])
interface AppComponent {
    fun login(loginActivity: LoginActivity)
    fun orders(orderListFragment: OrderListFragment)
    fun ordersSelect(orderSelectTypeScreen: OrderSelectTypeScreen)
    fun sliderScreen(sliderScreen: SliderScreen)
    fun drawing(drawingsFragment: DrawingsFragment)
}