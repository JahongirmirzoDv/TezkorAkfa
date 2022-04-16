package uz.algorithmgateway.supplier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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