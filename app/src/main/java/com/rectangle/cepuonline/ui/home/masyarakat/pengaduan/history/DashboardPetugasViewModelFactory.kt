package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rectangle.cepuonline.data.repositories.KeluhanRepository

class HistoryPengaduanViewModelFactory (private val repository: KeluhanRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryPengaduanViewModel(repository) as T
    }
}