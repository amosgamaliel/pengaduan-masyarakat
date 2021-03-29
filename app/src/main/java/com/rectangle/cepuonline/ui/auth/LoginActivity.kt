package com.rectangle.cepuonline.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.model.User
import com.rectangle.cepuonline.databinding.ActivityLoginBinding
import com.rectangle.cepuonline.ui.home.HomeActivity
import com.rectangle.cepuonline.ui.home.ajukan.AjukanKeluhanActivity
//import com.rectangle.cepuonline.databinding.ActivityLoginBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import com.rectangle.cepuonline.util.toast

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
                        val intent = Intent(this,HomeActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    ROLE_PETUGAS -> {
                        toast("Login petugas berhasil")
                    }
                    else -> {
                        toast("Error : role_id tidak valid")
                    }
                }
            }
        })
    }
    override fun onStarted() {
        toast("sedang login")
    }

    override fun onSuccess(user: User, token:String) {
        toast(token)
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
