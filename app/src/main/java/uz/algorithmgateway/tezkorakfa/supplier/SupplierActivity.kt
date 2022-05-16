package uz.algorithmgateway.tezkorakfa.supplier

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ActivitySupplierBinding

class SupplierActivity : AppCompatActivity() {
    lateinit var binding: ActivitySupplierBinding
    lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentViewSupplier) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )
    }

    fun bottomNavigationViewVisibilityGone() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun bottomNavigationViewVisibility() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}