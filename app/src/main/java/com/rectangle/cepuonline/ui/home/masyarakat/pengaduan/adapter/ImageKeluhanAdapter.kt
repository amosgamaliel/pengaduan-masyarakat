package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rectangle.cepuonline.R
import kotlinx.android.synthetic.main.list_item_image_aduan.view.*

class ImageKeluhanAdapter(private val aduanImages : ArrayList<Uri?>) : RecyclerView.Adapter<ImageKeluhanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_image_aduan, parent, false))
    }

    override fun getItemCount(): Int = aduanImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHero(aduanImages[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindHero(uri : Uri?) {
            itemView.apply {
                aduanImage.setImageURI(uri)

                this.deleteImage.setOnClickListener {
                    removeItem(uri)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) 0 else 1
    }

    fun removeItem(position: Uri?) {
        aduanImages.remove(position)
        notifyDataSetChanged()
    }
}