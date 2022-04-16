package uz.algorithmgateway.tezkorakfa.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.measurer.ui.MeasurerActivity
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref

/**
 * Created by Abrorjon Berdiyorov on 04.03.2022
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val sharedPref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.isLogin) {
                startActivity(Intent(this, MeasurerActivity::class.java))
            } else startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        finish()
    }
}