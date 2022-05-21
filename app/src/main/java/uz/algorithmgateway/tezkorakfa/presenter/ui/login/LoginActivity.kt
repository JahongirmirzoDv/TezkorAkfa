package uz.algorithmgateway.tezkorakfa.presenter.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.data.const.Value
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.ActivityLoginBinding
import uz.algorithmgateway.tezkorakfa.presenter.montage.MontageActivity
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.ServisMainActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.UIState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class LoginActivity : AppCompatActivity(), CoroutineScope {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding
    private val sharedPref by lazy { SharedPref(this) }
    private val TAG = "LoginActivity"
    private var back = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        MyApplication.appComponent.login(this)
        setContentView(binding.root)


        setupUI()
    }


    private fun setupUI() {
        binding.btnEnter.setOnClickListener {
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            if (phone.isNotEmpty() && password.isNotEmpty()) {
                launch(Dispatchers.Main) {
                    loginViewModel.loginUser(phone, password)
                    loginViewModel.state.collect {
                        when (it) {
                            is UIState.Loading -> {

                            }
                            is UIState.Error -> {
                                Toast.makeText(
                                    this@LoginActivity,
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is UIState.Success -> {
                                sharedPref.isLogin = true
                                sharedPref.userId = it.data?.userId.toString()
                                when (it.data?.roleId) {
                                    Value.SCALER -> {
                                        sharedPref.user_role = Value.SCALER
                                        val intent =
                                            Intent(this@LoginActivity, uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.MeasurerActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        startActivity(intent)
                                        finish()
                                    }
                                    Value.SUPPLIER -> {
                                        sharedPref.user_role = Value.SUPPLIER
                                        val intent =
                                            Intent(this@LoginActivity, ServisMainActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        startActivity(intent)
                                        finish()
                                    }
                                    Value.MONTAGER -> {
                                        sharedPref.user_role = Value.MONTAGER
                                        val intent =
                                            Intent(this@LoginActivity, MontageActivity::class.java)
                                        startActivity(intent)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        finish()
                                    }
                                    Value.SERVICER -> {
                                        sharedPref.user_role = Value.SERVICER
                                        val intent =
                                            Intent(
                                                this@LoginActivity,
                                                ServisMainActivity::class.java
                                            )
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (back) {
            super.onBackPressed()
            return
        }
        val handler = Handler(Looper.getMainLooper())
        this.back = true
        Toast.makeText(this, "Chiqish uchun ikki marta bosing", Toast.LENGTH_SHORT).show()
        handler.postDelayed({
            back = false
        }, 2000)
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}