package com.rectangle.cepuonline.ui.home.ajukan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.data.repositories.UserRepository
import com.rectangle.cepuonline.ui.auth.AuthViewModel

class AjukanKeluhanViewModelFactory (private val repository: KeluhanRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AjukanKeluhanViewModel(repository) as T
    }
}