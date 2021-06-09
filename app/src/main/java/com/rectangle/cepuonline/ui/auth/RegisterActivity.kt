package com.rectangle.cepuonline.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.databinding.ActivityRegisterBinding
import com.rectangle.cepuonline.ui.home.masyarakat.HomeMasyarakatActivity
import com.rectangle.cepuonline.ui.home.petugas.HomePetugasActivity
import com.rectangle.cepuonline.util.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.buttonLogin
import kotlinx.android.synthetic.main.activity_register.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), KodeinAware,AuthListener{
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        val viewModel = ViewModelProviders.of(this,factory  ).get(AuthViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.authListener = this

        viewModel.dataUser.observe(this, Observer { user->
            if (user!= null){
                when (user.role_id) {
                    ROLE_MASYARAKAT -> {
                        val intent = Intent(this,
                            HomeMasyarakatActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    ROLE_PETUGAS -> {
                        val intent = Intent(this,
                            HomePetugasActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else -> {
                        toast("Error : role_id tidak valid")
                    }
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
        buttonLogin.visibility = View.GONE
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        buttonLogin.visibility = View.VISIBLE
        alertDialogShow(this,"Register berhasil, silahkan login")
    }

    override fun onFailure(message: String) {
        root_layout.snackbar(message)
        buttonLogin.visibility = View.VISIBLE
        progress_bar.hide()
    }
}