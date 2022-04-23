package uz.algorithmgateway.tezkorakfa.measurer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import uz.algorithmgateway.tezkorakfa.databinding.ActivityMeasurerBinding
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import kotlin.coroutines.CoroutineContext


class MeasurerActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMeasurerBinding
    private var back = false
    private val sharedPref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override val coroutineContext: CoroutineContext
        get() = Job()

//    override fun onBackPressed() {
//        if (back) {
//            super.onBackPressed()
//            finishAffinity()
//        }
//        val handler = Handler(Looper.getMainLooper())
//        this.back = true
//        Toast.makeText(this, "Chiqish uchun ikki marta bosing", Toast.LENGTH_SHORT).show()
//        handler.postDelayed({
//            back = false
//        }, 2000)
//    }


}