package com.app.proyectpolleria

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class Google_maps_api : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private lateinit var btnBack: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps_api)

        createFragment()

        btnBack = findViewById(R.id.btnAtras)
        btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        finish()
    }



    private fun createFragment(){
        val mapFragment:SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        val coordinates = LatLng (-12.024778, -77.033028)
        val marker = MarkerOptions().position(coordinates).title("Polleria El Leñador")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }



}



























