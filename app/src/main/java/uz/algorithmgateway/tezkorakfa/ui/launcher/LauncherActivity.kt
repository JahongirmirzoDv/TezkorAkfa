package uz.algorithmgateway.tezkorakfa.ui.launcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.algorithmgateway.measurer.ui.MeasurerActivity
import org.koin.android.ext.android.get
import uz.algorithmgateway.tezkorakfa.ui.DesignerActivity
import uz.algorithmgateway.tezkorakfa.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.ui.splash.SplashActivity


class LauncherActivity : MvpAppCompatActivity(), LauncherView {

    @InjectPresenter
    internal lateinit var mPresenter: LauncherPresenter

    @ProvidePresenter
    fun providePresenter(): LauncherPresenter = get()


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isResultOk: Boolean = result.resultCode == Activity.RESULT_OK
            Toast.makeText(this, "onSplashResult", Toast.LENGTH_SHORT)
            mPresenter.onSplashResult(isResultOk, result.data)
        }


    override fun navigateToSplash() {
        val intent = Intent(this, SplashActivity::class.java)
        resultLauncher.launch(intent)

    }

    override fun navigateToLogin() {
        val intent = Intent(this, DesignerActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToMeasurer() {
        val intent = Intent(this, MeasurerActivity::class.java)
        startActivity(intent)
    }

    override fun showToast(string: String?) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun finishView() {
        finish()
    }
}