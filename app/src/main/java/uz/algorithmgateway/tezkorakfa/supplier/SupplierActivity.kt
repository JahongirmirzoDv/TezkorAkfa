package uz.algorithmgateway.tezkorakfa.supplier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.algorithmgateway.tezkorakfa.R

class SupplierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)

        val supplierFragment = SupplierFragment()
        supportFragmentManager.beginTransaction().apply {
            add(R.id.containerFragmentSupplier, supplierFragment)
            commit()
        }
    }
}