package uz.algorithmgateway.tezkorakfa.base

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mocklets.pluto.Pluto
import uz.algorithmgateway.tezkorakfa.di.AppComponent
import uz.algorithmgateway.tezkorakfa.di.DaggerAppComponent


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