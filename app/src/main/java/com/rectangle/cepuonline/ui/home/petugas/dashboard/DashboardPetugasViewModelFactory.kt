package com.rectangle.cepuonline.ui.home.petugas.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository

class DashboardPetugasViewModelFactory (private val repository: KeluhanRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardPetugasViewModel(repository) as T
    }
}