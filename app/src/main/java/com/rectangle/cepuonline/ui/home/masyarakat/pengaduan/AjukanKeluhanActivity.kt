package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.MyApi
import com.rectangle.cepuonline.data.network.NetworkConnectionInterceptor
import com.rectangle.cepuonline.data.network.response.PostPengaduanResponse
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.GridItemImageDecoration
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

class AjukanKeluhanActivity : AppCompatActivity(), KodeinAware {

    private lateinit var viewModel: PengaduanViewModel
    private var pengaduanId: Int? = null
    private var masyarakatIdBundle: Int? = null
    private var namaMasyarakat: String? = null
    private var subjekPengaduan: String? = null

    override val kodein by kodein()
    private val viewModelFactory: PengaduanViewModelFactory by instance()
    private var listUri = arrayListOf<Uri?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajukan_keluhan)

        val toolbar: Toolbar? = toolbar2
        toolbar?.title = "Tulis Pengaduan"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        pengaduanId = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).pengaduanId }
        masyarakatIdBundle = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).masyarakatId }
        namaMasyarakat = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).namaMasyarakat }
        subjekPengaduan = intent.extras?.let { AjukanKeluhanActivityArgs.fromBundle(it).subjekPengaduan }

        pengaduanId?.let {
            if(it != EMPTY_ARGS_INT) {
                toolbar?.title = "Buat Tanggapan"
                Toast.makeText(this@AjukanKeluhanActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        namaMasyarakat?.let {
            if(it != EMPTY_ARGS_STRING) {
                toPetugas.text = it
            }
        }

        subjekPengaduan?.let{
            if(it != EMPTY_ARGS_STRING) {
                subjek_editText.setText("Tanggapan untuk pengaduan anda : $it")
            }
        }


//        val binding : ActivityAjukanKeluhanBinding = DataBindingUtil.setContentView(this,R.layout.activity_ajukan_keluhan)
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//            .get(AjukanKeluhanViewModel::class.java)
//
//        binding.viewModel = viewModel

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
            } else {
                listUri.add(data?.data)
            }
        }
        val imageKeluhanAdapter =
            ImageKeluhanAdapter(
                listUri
            )
        rvImagePengaduan.apply {
            adapter = imageKeluhanAdapter
            addItemDecoration(GridItemImageDecoration(5))
            val mlayoutManager = GridLayoutManager(context, 2)
            mlayoutManager.orientation = LinearLayoutManager.VERTICAL
            if (listUri.size % 2 == 0) {
                layoutManager = mlayoutManager
            } else {
                mlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when (adapter?.getItemViewType(position)) {
                            1 -> 1
                            0 -> 2 //number of columns of the grid
                            else -> -1
                        }
                    }
                }
                layoutManager = mlayoutManager
            }
        }
    }

    @NonNull
    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM, descriptionString
        )
    }

    @NonNull
    fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
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


    private fun postPengaduan(idMasyarakat : Int) {
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

            MyApi(NetworkConnectionInterceptor(this)).uploadFiles(
                subjek,
                isiLaporan,
                masyarakatId,
                parts
            ).enqueue(object :
                Callback<PostPengaduanResponse> {
                override fun onFailure(call: Call<PostPengaduanResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: " + t.message)
                }

                override fun onResponse(
                    call: Call<PostPengaduanResponse>,
                    response: Response<PostPengaduanResponse>
                ) {
                    response.body()?.let {
                        Coroutines.main {
                            alertDialogShow(this@AjukanKeluhanActivity, it.message)
                        }
                    }
                }
            })

        }
    }

    private fun postTanggapan(idPengaduan: Int, idMasyarakat: Int) {
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
            MyApi(NetworkConnectionInterceptor(this)).uploadFilesTanggapan(
                idPengaduan,
                subjek,
                isiLaporan,
                masyarakatId,
                parts
            ).enqueue(object :
                Callback<PostPengaduanResponse> {
                override fun onFailure(call: Call<PostPengaduanResponse>, t: Throwable) {
                    Log.d("TAG", "onFailure: " + t.message)
                }

                override fun onResponse(
                    call: Call<PostPengaduanResponse>,
                    response: Response<PostPengaduanResponse>
                ) {
                    response.body()?.let {
                        Coroutines.main {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_postingan, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when {
            id == R.id.openAttachment -> {
                openImageChooser()
            }
            id == R.id.sendPosting -> {
                pengaduanId.let {
                    it?.let {
                        if (masyarakatIdBundle != null) {
                            postTanggapan(it, masyarakatIdBundle!!)
                        } else {
                            Toast.makeText(
                                this@AjukanKeluhanActivity,
                                "Parameter activity tidak ditemukan",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    if (it == EMPTY_ARGS_INT)
                        postPengaduan(masyarakatIdBundle!!)
                }
            }
            id == android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}