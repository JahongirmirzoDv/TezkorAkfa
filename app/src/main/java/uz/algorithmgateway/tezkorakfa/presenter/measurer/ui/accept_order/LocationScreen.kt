package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.accept_order

import android.Manifest
import android.app.ActivityManager
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.LocationScreenBinding
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.accept_order.service.LocationService
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import kotlin.coroutines.CoroutineContext

class LocationScreen : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerDragListener, CoroutineScope {
    private var _binding: LocationScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var marker: Marker
    lateinit var googleApiClient: GoogleApiClient
    lateinit var googleMap: GoogleMap
    private val sharedPref by lazy { SharedPref(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LocationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleApiClient = getAPIClientInstance()
        if (googleApiClient != null) {
            googleApiClient.connect()
        }


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.save.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0

        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) { /* ... */
                    requestGPSSettings()
                    startLocationService()
                    googleMap.isMyLocationEnabled = true
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?,
                ) { /* ... */
                }
            }).check()


        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(41.2995, 69.2401), 8f))

        val sydney = LatLng(41.29, 69.24)
        googleMap.addMarker(MarkerOptions().position(sydney).draggable(true)
            .title("Marker in Tashkent"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onMapClick(p0: LatLng) {

    }

    override fun onMarkerDrag(p0: Marker) {

    }

    override fun onMarkerDragEnd(p0: Marker) {
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun getAPIClientInstance(): GoogleApiClient {
        return GoogleApiClient.Builder(requireContext())
            .addApi(LocationServices.API).build()
    }

    private fun requestGPSSettings() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 500
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status: Status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    Log.i("", "All location settings are satisfied.")
                    Toast.makeText(requireContext(), "GPS yoqilgan", Toast.LENGTH_SHORT)
                        .show()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        "",
                        "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings "
                    )
                    try {
                        status.startResolutionForResult(requireActivity(), 0x1)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("Applicationsett", e.toString())
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    Log.i(
                        "",
                        "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created."
                    )
                    Toast.makeText(
                        requireContext(),
                        "Location settings are inadequate, and cannot be fixed here",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isLocationServiceRunnig(): Boolean {
        val manager =
            requireActivity().getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (LocationService::class.java.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }

    private fun startLocationService() {
        if (!isLocationServiceRunnig()) {
            val intent = Intent(requireContext(), LocationService::class.java)
            intent.action = "startLocationService"
            requireActivity().startService(intent)
            Toast.makeText(requireContext(), "Service running", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(requireContext(), LocationService::class.java)
        intent.action = "startLocationService"
        requireActivity().stopService(intent)
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}