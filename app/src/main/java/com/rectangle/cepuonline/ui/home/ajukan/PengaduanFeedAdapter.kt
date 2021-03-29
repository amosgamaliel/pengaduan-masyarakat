package com.rectangle.cepuonline.ui.home.ajukan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.databinding.ListItemFeedPengaduanBinding

class PengaduanFeedAdapter(): ListAdapter<PengaduanResponse, PengaduanFeedAdapter.ViewHolder>(PengaduanFeedDiffCallback()) {

    var data = listOf<PengaduanResponse>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder private constructor(private val binding: ListItemFeedPengaduanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PengaduanResponse) {
            binding.pengaduan = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFeedPengaduanBinding.inflate(layoutInflater, parent, false)


                return ViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class PengaduanFeedDiffCallback  : DiffUtil.ItemCallback<PengaduanResponse>() {
    override fun areItemsTheSame(oldItem: PengaduanResponse, newItem: PengaduanResponse): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PengaduanResponse, newItem: PengaduanResponse): Boolean {
        return oldItem.id == newItem.id
    }
}
