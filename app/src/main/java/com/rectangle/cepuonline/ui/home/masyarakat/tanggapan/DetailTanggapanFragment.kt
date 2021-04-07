package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.databinding.FragmentDetailTanggapanBinding
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.GridItemImageDecoration
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.ImageKeluhanDataAdapter
import com.rectangle.cepuonline.ui.home.petugas.tanggapan.DetailPengaduanFragment
import com.rectangle.cepuonline.ui.home.petugas.tanggapan.DetailPengaduanFragmentDirections
import com.rectangle.cepuonline.ui.home.petugas.tanggapan.ID_PENGADUAN
import com.rectangle.cepuonline.util.AvatarGenerator
import com.rectangle.cepuonline.util.tanggalPengaduan
import kotlinx.android.synthetic.main.fragment_detail_pengaduan.*
import kotlinx.android.synthetic.main.fragment_detail_tanggapan.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailTanggapanFragment : Fragment(),KodeinAware{
    private lateinit var viewModel: MasyarakatsTanggapanViewModel
    private lateinit var binding: FragmentDetailTanggapanBinding
    var idPengaduan : Int? = null
    override val kodein by kodein()
    private val viewModelFactory: MasyarakatsTanggapanViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_tanggapan, container, false)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MasyarakatsTanggapanViewModel::class.java)

//        viewModel.getDetailTanggapan(35)
        idPengaduan = arguments?.getInt(ID_PENGADUAN)

        idPengaduan?.let {
            if(it != 0){
                viewModel.getDetailTanggapan(it)
            }
        }

        viewModel.tanggapan.observe(viewLifecycleOwner, Observer {
            it?.let { tanggapan ->
                idPengaduan = tanggapan.pengaduan.id
                binding.isiPengaduan.text = tanggapan.isi_tanggapan
                binding.subjekPengaduan.text = tanggapan.subjek
                binding.namaPengadu.text = tanggapan.nama
                binding.tanggalPengaduan.tanggalPengaduan(tanggapan)
                binding.subjekPengaduans.text = "Balasan untuk pengaduan anda : "+tanggapan.pengaduan.subjek
                binding.avatarProfile.setImageDrawable(
                    context?.let { it1 ->
                        AvatarGenerator.avatarImage(
                            it1,
                            100,
                            1,
                            tanggapan.nama
                        )
                    })

                val adapter =
                    ImageKeluhanDataAdapter(
                        tanggapan.image
                    )

                binding.imagePengaduanFeedRv.let{
                    it.adapter = adapter
                    it.addItemDecoration(GridItemImageDecoration(5))
                    val mlayoutManager = GridLayoutManager(context, 2)
                    mlayoutManager.orientation = LinearLayoutManager.VERTICAL
                    if (tanggapan.image.size % 2 == 0) {
                        it.layoutManager = mlayoutManager
                    } else {
                        mlayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return when (adapter.getItemViewType(position)) {
                                    1 -> 1
                                    0 -> 2 //number of columns of the grid
                                    else -> -1
                                }
                            }
                        }
                        it.layoutManager = mlayoutManager
                    }
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.idTanggapan.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getDetailTanggapan(it)
            }
        })

        binding.subjekPengaduans.setOnClickListener {
            this.findNavController().navigate(DetailTanggapanFragmentDirections.actionDetailTanggapanFragmentToDetailPengaduanFragment(true,idPengaduan!!))
        }
    }
}