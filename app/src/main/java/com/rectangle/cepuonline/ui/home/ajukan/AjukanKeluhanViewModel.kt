package com.rectangle.cepuonline.ui.home.ajukan

import android.net.Uri
import android.os.FileUtils
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.rectangle.cepuonline.data.repositories.KeluhanRepository
import com.rectangle.cepuonline.ui.auth.AuthListener
import com.rectangle.cepuonline.util.getFileName
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class AjukanKeluhanViewModel(private val repository: KeluhanRepository) : ViewModel() {
    var subjekKeluhan:String? = null
    var isiKeluhan:String? = null
    var authListener: AuthListener? = null
    var name:String? = null
    var passwordconfirm:String? = null
}