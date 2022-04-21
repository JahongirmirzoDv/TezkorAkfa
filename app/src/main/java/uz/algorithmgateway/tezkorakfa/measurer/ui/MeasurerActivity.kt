package uz.algorithmgateway.tezkorakfa.measurer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.databinding.ActivityMeasurerBinding
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref


class MeasurerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasurerBinding
    private var back = false
    private val sharedPref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

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