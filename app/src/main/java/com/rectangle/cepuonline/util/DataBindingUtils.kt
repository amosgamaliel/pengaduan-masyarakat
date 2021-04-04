package com.rectangle.cepuonline.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.response.ImagePengaduan
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.GridItemImageDecoration
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.ImageKeluhanDataAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


//@BindingAdapter(value = ["setBooks"])
//fun RecyclerView.setImages(books: List<ImagePengaduan>?) {
//    if (books != null) {
//        val bookAdapter = PengaduanFeedAdapter()
//        bookAdapter.submitList(books)
//
//        adapter = bookAdapter
//    }
//}

@SuppressLint("SimpleDateFormat")
fun toSimpleString(date: Date) : String {
    val format = SimpleDateFormat("dd MMM")
    return format.format(date)
}

@BindingAdapter("listImage")
fun RecyclerView.listImage(books: ArrayList<ImagePengaduan>?) {
    if (books != null) {
        val bookAdapter =
            ImageKeluhanDataAdapter(
                books
            )

        adapter = bookAdapter
        addItemDecoration(GridItemImageDecoration(5))
        val mlayoutManager = GridLayoutManager(context, 2)
        mlayoutManager.orientation = LinearLayoutManager.VERTICAL
        if (books.size % 2 == 0) {
            layoutManager = mlayoutManager
            return;
        }else {
            mlayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (bookAdapter.getItemViewType(position)) {
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

@BindingAdapter("subjekPengaduan")
fun TextView.subjekPengaduan(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        if(pengaduan.status == 0){
            typeface = Typeface.DEFAULT_BOLD
        }
        text = pengaduan.subjek
    }
}

@BindingAdapter("namaPengadu")
fun TextView.namaPengadu(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        if(pengaduan.status == 0){
            typeface = Typeface.DEFAULT_BOLD
        }
        text = pengaduan.namaPengadu
    }
}

@BindingAdapter("dumbAvatar")
fun ImageView.dumbAvatar(pengaduan : PengaduanResponse?){
    pengaduan?.let{
        setImageDrawable(AvatarGenerator.avatarImage(
                context,
                100,
                1,
                pengaduan.namaPengadu
            ))
    }
}

@BindingAdapter("tanggalPengaduan")
fun TextView.tanggalPengaduan(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        val formatDate = SimpleDateFormat("E, dd MMM yyyy")
        val formatTime = SimpleDateFormat("HH.mm")
        val tanggal = formatDate.format(pengaduan.tanggal_pengaduan)
        val waktu = formatTime.format(pengaduan.tanggal_pengaduan)
        
        
        text = String.format(context.getString(R.string.pukul_string),tanggal,waktu)
    }
}

@BindingAdapter("tanggalPengaduanShort")
fun TextView.tanggalPengaduanShort(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        if(pengaduan.status == 0){
            typeface = Typeface.DEFAULT_BOLD
        }
        val format = SimpleDateFormat("dd MMM")
        val tanggal = format.format(pengaduan.tanggal_pengaduan)
        text = tanggal
    }
}

@BindingAdapter("isiLaporan")
fun TextView.isiLaporan(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        text = pengaduan.isi_laporan
    }
}