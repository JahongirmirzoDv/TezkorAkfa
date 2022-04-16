package uz.algorithmgateway.tezkorakfa.measurer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.databinding.ActivityMeasurerBinding


class MeasurerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasurerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}