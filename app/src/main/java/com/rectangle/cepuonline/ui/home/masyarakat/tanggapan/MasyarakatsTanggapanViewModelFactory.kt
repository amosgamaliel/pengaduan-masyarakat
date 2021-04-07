package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModel

class MasyarakatsTanggapanViewModelFactory (private val repository: KeluhanRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MasyarakatsTanggapanViewModel(repository) as T
    }
}