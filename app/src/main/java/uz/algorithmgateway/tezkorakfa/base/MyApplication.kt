package uz.algorithmgateway.tezkorakfa.base

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import uz.algorithmgateway.core.di.apiModule
import uz.algorithmgateway.core.di.configModule
import uz.algorithmgateway.tezkorakfa.BuildConfig

/**
 * Created by Abrorjon Berdiyorov on 03.03.2022
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()


        AndroidThreeTen.init(this)

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(listOf(configModule, apiModule, presentationModule))
        }
    }

}