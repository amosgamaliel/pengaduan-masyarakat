package com.rectangle.cepuonline.ui.home.petugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rectangle.cepuonline.R

class HomePetugasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_petugas)

        val navController = findNavController(R.id.home_petugas_nav_host)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)



    }
}