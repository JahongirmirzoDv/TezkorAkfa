package uz.algorithmgateway.tezkorakfa.servis.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.databinding.ActivityServisMainBinding

class ServisMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityServisMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServisMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}