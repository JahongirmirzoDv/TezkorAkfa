package uz.algorithmgateway.tezkorakfa.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get
import uz.algorithmgateway.measurer.ui.MeasurerActivity
import uz.algorithmgateway.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.databinding.ActivityLoginBinding
import uz.algorithmgateway.tezkorakfa.ui.DesignerActivity

/**
 * Created by Abrorjon Berdiyorov on 04.03.2022
 */


class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding


    @InjectPresenter
    internal lateinit var mPresenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btnEnter.setOnClickListener { onEnterClick() }
    }

    private fun onEnterClick() {
        val phone: String = binding.etPhone.text.toString()
        val password: String = binding.etPassword.text.toString()
        mPresenter.onEnterClick(phone, password)
    }

    override fun finishView() {
        finish()
    }

    override fun showToast(string: String?) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun showNetworkError() {
        Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
        Toast.makeText(this, "hideProgress", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        Toast.makeText(this, "showProgress", Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToMeasure() {
        val intent = Intent(this, MeasurerActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToSupplier() {
        val intent = Intent(this, SupplierActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToMontage() {
    }

    override fun navigateToService() {
    }

}