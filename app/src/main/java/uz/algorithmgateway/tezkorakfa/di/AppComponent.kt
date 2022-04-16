package uz.algorithmgateway.tezkorakfa.di

import dagger.Component
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun login(loginActivity: LoginActivity)
}