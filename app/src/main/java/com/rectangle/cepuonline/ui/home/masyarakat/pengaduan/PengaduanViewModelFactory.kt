package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository

class PengaduanViewModelFactory (private val repository: KeluhanRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PengaduanViewModel(repository) as T
    }
}