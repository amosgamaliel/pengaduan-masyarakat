package com.rectangle.cepuonline.ui.home.masyarakat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.RoomDatabase
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.db.AppDatabase
import com.rectangle.cepuonline.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_home_masyarakat.*

class HomeMasyarakatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_masyarakat)

        val navController = findNavController(R.id.home_nav_host)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

//        val badge = bottom_nav.getOrCreateBadge(R.id.feedPengaduanFragment)
//        badge.isVisible = true
//// An icon only badge will be displayed unless a number is set:
//        badge.number = 2
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
        //Or Remove badge
    }

}