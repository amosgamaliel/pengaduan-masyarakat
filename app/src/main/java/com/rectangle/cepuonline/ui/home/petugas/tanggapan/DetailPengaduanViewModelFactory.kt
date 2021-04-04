package com.rectangle.cepuonline.ui.home.petugas.tanggapan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModel

class DetailPengaduanViewModelFactory (private val repository: KeluhanRepository, private val idPengaduan : Int):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailPengaduanViewModel(repository) as T
    }
}