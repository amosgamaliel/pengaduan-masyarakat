package com.rectangle.cepuonline.ui.home.petugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_home_masyarakat.*

class HomePetugasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_petugas)

        val navController = findNavController(R.id.home_petugas_nav_host)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        val builder = AlertDialog.Builder(this)

        logout.setOnClickListener {
            builder.setTitle("Keluar?")
            builder.setMessage("Apakah anda ingin keluar dari akun ini?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                AppDatabase.clearTables()
                finish()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

    }
}