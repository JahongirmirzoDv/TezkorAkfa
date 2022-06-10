package uz.algorithmgateway.tezkorakfa.presenter.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.data.const.Value
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.MeasurerActivity
import uz.algorithmgateway.tezkorakfa.presenter.montage.MontageActivity
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.ServisMainActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val sharedPref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.isLogin) {
                when (sharedPref.user_role) {
                    Value.SCALER -> {
                        val intent =
                            Intent(this, MeasurerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Value.SUPPLIER -> {
                        val intent =
                            Intent(this, SupplierActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Value.MONTAGER -> {
                        val intent =
                            Intent(this, MontageActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Value.SERVICER -> {
                        val intent =
                            Intent(this, ServisMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            } else startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        finish()
    }
}