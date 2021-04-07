package com.rectangle.cepuonline.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.databinding.ActivityLoginBinding
import com.rectangle.cepuonline.ui.home.masyarakat.HomeMasyarakatActivity
import com.rectangle.cepuonline.ui.home.petugas.HomePetugasActivity
import com.rectangle.cepuonline.util.*
//import com.rectangle.cepuonline.databinding.ActivityLoginBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import kotlinx.android.synthetic.main.activity_login.*

private const val ROLE_PETUGAS = 2
private const val ROLE_MASYARAKAT = 3
class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()

    private val factory : AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)

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
        buttonLogin.visibility = GONE
//        toast("sedang login")
    }

    override fun onSuccess(user: User, token:String) {
        progress_bar.hide()
        buttonLogin.visibility = VISIBLE
    }

    override fun onFailure(message: String) {
//        toast(message)
        progress_bar.hide()
        alertDialogAuth(this,message)
        buttonLogin.visibility = VISIBLE
        edtID.setText("")
        edtPass.setText("")
    }
}
