package com.rectangle.cepuonline.ui.home.petugas.tanggapan

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.databinding.FragmentDetailPengaduanBinding
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.GridItemImageDecoration
import com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.adapter.ImageKeluhanDataAdapter
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModel
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModelFactory
import com.rectangle.cepuonline.util.AvatarGenerator
import com.rectangle.cepuonline.util.parseDate
import kotlinx.android.synthetic.main.fragment_detail_pengaduan.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailPengaduanFragment : Fragment(), KodeinAware, PengaduanListener {
    private lateinit var viewModel: DashboardPetugasViewModel
    private lateinit var binding: FragmentDetailPengaduanBinding
    override val kodein by kodein()
    private val viewModelFactory: DashboardPetugasViewModelFactory by instance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_pengaduan, container, false)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(DashboardPetugasViewModel::class.java)

        viewModel.idPengaduan.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getPengaduanFromApi(it)
            }
        })



        viewModel.pengaduan.observe(viewLifecycleOwner, Observer {
            it?.let { pengaduan ->
                binding.isiPengaduan.text = pengaduan.isi_laporan
                binding.subjekPengaduan.text = pengaduan.subjek
                binding.namaPengadu.text = pengaduan.namaPengadu
                binding.avatarProfile.setImageDrawable(
                    context?.let { it1 ->
                        AvatarGenerator.avatarImage(
                            it1,
                            100,
                            1,
                            pengaduan.namaPengadu
                        )
                    })
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
                try {
                    val time: Long = pengaduan.tanggal_pengaduan.time
                    val now = System.currentTimeMillis()
                    val ago =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS)
                    binding.tanggalPengaduan.text = ago
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
//                val niceDateStr: String = DateUtils.getRelativeTimeSpanString(
//                    pengaduan.tanggal_pengaduan.getTime(),
//                    Calendar.getInstance().timeInMillis,
//                    DateUtils.MINUTE_IN_MILLIS
//                ) as String
//
//                binding.tanggalPengaduan.text = niceDateStr
                pengaduan.tanggal_pengaduan.let {
                    binding.tanggalPengaduan.text = it.parseDate(it)
                }

                val adapter =
                    ImageKeluhanDataAdapter(
                        pengaduan.image
                    )

                binding.imagePengaduanFeedRv.let{
                    it.adapter = adapter
                    it.addItemDecoration(GridItemImageDecoration(5))
                    val mlayoutManager = GridLayoutManager(context, 2)
                    mlayoutManager.orientation = LinearLayoutManager.VERTICAL
                    if (pengaduan.image.size % 2 == 0) {
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

        tulisBalasButton.setOnClickListener {
            this.findNavController().navigate(DetailPengaduanFragmentDirections.actionDetailPengaduanFragmentToAjukanKeluhanActivity(viewModel.idPengaduan.value!!,viewModel.pengaduan.value!!.masyarakat_id,viewModel.pengaduan.value!!.namaPengadu,viewModel.pengaduan.value!!.subjek))
        }
    }

    override fun onSuccess(pengaduan: PengaduanResponse) {
        binding.isiPengaduan.text = pengaduan.isi_laporan
        binding.subjekPengaduan.text = pengaduan.subjek
        binding.namaPengadu.text = pengaduan.namaPengadu
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }
}