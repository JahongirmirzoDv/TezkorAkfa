package uz.algorithmgateway.tezkorakfa.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity

/**
 * Created by Abrorjon Berdiyorov on 04.03.2022
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            setResult(Activity.RESULT_OK, Intent(this,LoginActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED, Intent())
        finish()
    }
}