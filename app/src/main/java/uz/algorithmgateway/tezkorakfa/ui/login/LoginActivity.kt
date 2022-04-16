package uz.algorithmgateway.tezkorakfa.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.ActivityLoginBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.MeasurerActivity
import uz.algorithmgateway.tezkorakfa.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class LoginActivity : AppCompatActivity(), CoroutineScope {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding
    private val sharedPref by lazy { SharedPref(this) }
    private val TAG = "LoginActivity"


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
                                Toast.makeText(this@LoginActivity,
                                    it.message,
                                    Toast.LENGTH_SHORT).show()
                                Log.e(TAG, "setupUI: ${it.message}")
                            }
                            is UIState.Success -> {
                                sharedPref.isLogin = true
                                val intent =
                                    Intent(this@LoginActivity, MeasurerActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}