package uz.algorithmgateway.tezkorakfa.base

import android.app.Application
import com.mocklets.pluto.Pluto
import uz.algorithmgateway.tezkorakfa.di.component.AppComponent
import uz.algorithmgateway.tezkorakfa.di.component.DaggerAppComponent


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Pluto.initialize(this)
        appComponent = DaggerAppComponent.builder()
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: MyApplication
    }

}