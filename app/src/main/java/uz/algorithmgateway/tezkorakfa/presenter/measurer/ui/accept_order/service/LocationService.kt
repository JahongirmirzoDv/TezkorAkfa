package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.accept_order.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.accept_order.model.Locations
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext


@Singleton
class LocationService : Service(), CoroutineScope {
    val _location = MutableStateFlow<Locations?>(null)
    private val sharedPref by lazy { SharedPref(this) }

    var locationCallbak = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            launch {
                _location.emit(
                    Locations(
                        locationResult.lastLocation.latitude,
                        locationResult.lastLocation.longitude
                    )
                )
                _location.collect {
                    val toJson = Gson().toJson(it)
                    sharedPref.location = toJson
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException("not")
    }

    fun startService() {
        var locationRequest = LocationRequest()
        locationRequest.interval = 1
        locationRequest.fastestInterval = 1
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
            locationRequest, locationCallbak,
            Looper.getMainLooper()
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startService()
        return super.onStartCommand(intent, flags, startId)
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}
