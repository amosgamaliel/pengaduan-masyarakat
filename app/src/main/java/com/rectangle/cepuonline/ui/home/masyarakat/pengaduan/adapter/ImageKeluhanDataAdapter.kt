package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.REMOTE_URL
import com.rectangle.cepuonline.data.network.response.ImagePengaduan
import kotlinx.android.synthetic.main.list_item_image_aduan.view.*


class ImageKeluhanDataAdapter(private val aduanImages: ArrayList<ImagePengaduan?>) :
    RecyclerView.Adapter<ImageKeluhanDataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_image_aduan, parent, false)
        )
    }

    override fun getItemCount(): Int = aduanImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindImage(aduanImages[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindImage(imagePengaduan: ImagePengaduan?) {

            itemView.apply {
                if (itemCount == 1) {
                    aduanImage.layoutParams.height = WRAP_CONTENT
                }
                Glide.with(this)
                    .load(REMOTE_URL + "/images/" + imagePengaduan?.image_url)
                    .into(aduanImage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) 0 else 1
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

}