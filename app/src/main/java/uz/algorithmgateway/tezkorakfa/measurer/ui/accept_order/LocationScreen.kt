package uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uz.algorithmgateway.tezkorakfa.R

class LocationScreen : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(41.2995, 69.2401), 4f))

        val sydney = LatLng(41.29, 69.24)
        googleMap.addMarker(MarkerOptions().position(sydney).draggable(true)
            .title("Marker in Tashkent"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.location_screen_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}