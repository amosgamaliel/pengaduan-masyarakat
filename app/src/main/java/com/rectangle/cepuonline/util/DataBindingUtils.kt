package com.rectangle.cepuonline.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rectangle.cepuonline.data.network.response.ImagePengaduan
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.ui.home.ajukan.adapter.GridItemImageDecoration
import com.rectangle.cepuonline.ui.home.ajukan.adapter.ImageKeluhanDataAdapter


//@BindingAdapter(value = ["setBooks"])
//fun RecyclerView.setImages(books: List<ImagePengaduan>?) {
//    if (books != null) {
//        val bookAdapter = PengaduanFeedAdapter()
//        bookAdapter.submitList(books)
//
//        adapter = bookAdapter
//    }
//}


@BindingAdapter("listImage")
fun RecyclerView.listImage(books: ArrayList<ImagePengaduan?>?) {
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
        text = pengaduan.subjek
    }
}

@BindingAdapter("namaPengadu")
fun TextView.namaPengadu(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        text = pengaduan.namaPengadu
    }
}

@BindingAdapter("tanggalPengaduan")
fun TextView.tanggalPengaduan(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        text = pengaduan.tanggal_pengaduan
    }
}

@BindingAdapter("isiLaporan")
fun TextView.isiLaporan(pengaduan: PengaduanResponse?) {
    pengaduan?.let{
        text = pengaduan.isi_laporan
    }
}