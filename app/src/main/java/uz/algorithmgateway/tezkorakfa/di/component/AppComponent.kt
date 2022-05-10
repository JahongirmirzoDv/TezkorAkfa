package uz.algorithmgateway.tezkorakfa.di.component

import dagger.Component
import uz.algorithmgateway.tezkorakfa.di.module.ApplicationModule
import uz.algorithmgateway.tezkorakfa.di.module.DatabaseModule
import uz.algorithmgateway.tezkorakfa.di.module.NetworkModule
import uz.algorithmgateway.tezkorakfa.measurer.ui.MeasurerActivity
import uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order.AcceptOrderScreen
import uz.algorithmgateway.tezkorakfa.measurer.ui.confirm_orders.ConfirmOrdersScreen
import uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo.CustomerTakePhotoFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.DrawingsFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.save_pdf.SavePdfFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList.OrderListFragment
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_count.ItemCountScreen
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.OrderSelectTypeScreen
import uz.algorithmgateway.tezkorakfa.measurer.ui.signature.SignatureFragment
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
    fun confirm(confirmOrdersScreen: ConfirmOrdersScreen)
    fun savePdf(savePdfFragment: SavePdfFragment)
    fun accept(acceptOrderScreen: AcceptOrderScreen)
    fun counter(itemCountScreen: ItemCountScreen)
    fun signature(signatureFragment: SignatureFragment)
    fun photo(customerTakePhotoFragment: CustomerTakePhotoFragment)
    fun measure(measurerActivity: MeasurerActivity)
}