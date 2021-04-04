package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.NetworkConnectionInterceptor
import com.rectangle.cepuonline.data.network.response.PostPengaduanResponse
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.ImageKeluhanAdapter
import com.rectangle.cepuonline.util.Coroutines
import com.rectangle.cepuonline.util.alertDialogShow
import com.rectangle.cepuonline.util.getFileName
import com.rectangle.cepuonline.util.snackbar
import kotlinx.android.synthetic.main.activity_ajukan_keluhan.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AjukanKeluhanActivity : AppCompatActivity(),KodeinAware{

    private lateinit var viewModel: PengaduanViewModel
    override val kodein by kodein()
    private val viewModelFactory: PengaduanViewModelFactory by instance()
    private var listUri = arrayListOf<Uri?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajukan_keluhan)

        val pengaduanId = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).pengaduanId }
        val masyarakatId = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).masyarakatId }

        pengaduanId.let {
            it?.let {
                Toast.makeText(this@AjukanKeluhanActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }

//        val binding : ActivityAjukanKeluhanBinding = DataBindingUtil.setContentView(this,R.layout.activity_ajukan_keluhan)
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//            .get(AjukanKeluhanViewModel::class.java)
//
//        binding.viewModel = viewModel

        ajukanBtn.setOnClickListener {
            pengaduanId.let {
                it?.let {
                    if (masyarakatId != null) {
                        postTanggapan(it,masyarakatId)
                    }else{
                        Toast.makeText(this@AjukanKeluhanActivity, "Parameter activity tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                    return@setOnClickListener
                }
                return@let
            }
            postPengaduan()
        }

        pilihGambar.setOnClickListener {
            openImageChooser()
        }

    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            it.action = Intent.ACTION_GET_CONTENT
//            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val clipData = data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val item = clipData.getItemAt(i)
                    val uri = item.uri

                    listUri.add(uri)
                }
            }else{
                listUri.add(data?.data)
            }
        }
        val imageKeluhanAdapter =
            ImageKeluhanAdapter(
                listUri
            )
        rvImagePengaduan.apply{
            layoutManager = GridLayoutManager(this@AjukanKeluhanActivity,2, GridLayoutManager.VERTICAL,false)
            adapter = imageKeluhanAdapter
        }
    }

    @NonNull
    fun createPartFromString(descriptionString:String): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, descriptionString)
    }

    @NonNull
    fun prepareFilePart(partName:String, fileUri: Uri): MultipartBody.Part {
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(fileUri, "r", null)

        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(fileUri))
        val requestFile = RequestBody.create(
            MediaType.parse(contentResolver.getFileName(fileUri)),
            file
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }



    private fun postPengaduan() {
        Coroutines.io {
            val parts = arrayListOf<MultipartBody.Part>()
            if (listUri.isEmpty()) {
                layout_root.snackbar("Gambar belum ada")
                return@io
            }

            listUri.forEach { uri ->
                parts.add(prepareFilePart("photo[]", uri!!))
            }

            val subjek = createPartFromString(subjek_editText.text.toString())
            val isiLaporan = createPartFromString(isiLaporan_editText.text.toString())
            val masyarakatId = createPartFromString("3")

            MyApi(NetworkConnectionInterceptor(this)).uploadFiles(subjek, isiLaporan, masyarakatId, parts).enqueue(object :
                Callback<PostPengaduanResponse> {
                override fun onFailure(call: Call<PostPengaduanResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: " + t.message)
                }

                override fun onResponse(
                    call: Call<PostPengaduanResponse>,
                    response: Response<PostPengaduanResponse>
                ) {
                    response.body()?.let {
                        Coroutines.main{
                            alertDialogShow(this@AjukanKeluhanActivity, it.message)
                        }
                    }
                }
            })

        }
    }

    private fun postTanggapan(idPengaduan : Int, idMasyarakat : Int) {
        Coroutines.io {
            val parts = arrayListOf<MultipartBody.Part>()
            if (listUri.isEmpty()) {
                layout_root.snackbar("Gambar belum ada")
                return@io
            }

            listUri.forEach { uri ->
                parts.add(prepareFilePart("photo[]", uri!!))
            }

            val subjek = createPartFromString(subjek_editText.text.toString())
            val isiLaporan = createPartFromString(isiLaporan_editText.text.toString())
            val masyarakatId = createPartFromString(idMasyarakat.toString())


//            progress_bar.progress = 0
            MyApi(NetworkConnectionInterceptor(this)).uploadFilesTanggapan(idPengaduan,subjek, isiLaporan, masyarakatId, parts).enqueue(object :
                Callback<PostPengaduanResponse> {
                override fun onFailure(call: Call<PostPengaduanResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: " + t.message)
                }

                override fun onResponse(
                    call: Call<PostPengaduanResponse>,
                    response: Response<PostPengaduanResponse>
                ) {
                    response.body()?.let {
                        Coroutines.main{
                            alertDialogShow(this@AjukanKeluhanActivity, it.message)
                        }
                    }
                }
            })

        }
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

}