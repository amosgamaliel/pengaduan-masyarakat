package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.data.network.response.TanggapanResponse
import com.rectangle.cepuonline.databinding.ListItemPengaduanLinearBinding
import com.rectangle.cepuonline.databinding.ListItemTanggapanLinearBinding
import com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.listener.DashboardPetugasListener

class MasyarakatTanggapansAdapter(private val clickListener : TanggapanListener) : ListAdapter<PengaduanResponse, MasyarakatTanggapansAdapter.ViewHolder>(
    MasyarakatTanggapansDiffCallback()
) {

    var data = listOf<TanggapanResponse>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder private constructor(private val binding: ListItemTanggapanLinearBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TanggapanResponse, clickListener: TanggapanListener) {
            binding.tanggapan = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTanggapanLinearBinding.inflate(layoutInflater, parent, false)


                return ViewHolder(
                    binding
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item,clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class MasyarakatTanggapansDiffCallback  : DiffUtil.ItemCallback<PengaduanResponse>() {
    override fun areItemsTheSame(oldItem: PengaduanResponse, newItem: PengaduanResponse): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PengaduanResponse, newItem: PengaduanResponse): Boolean {
        return oldItem.id == newItem.id
    }
}
