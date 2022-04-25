package uz.algorithmgateway.tezkorakfa.measurer.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
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
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?,
                ) { /* ... */
                }
            }).check()
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