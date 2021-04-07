package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.data.network.response.PengaduanResponse
import com.rectangle.cepuonline.databinding.HistoryPengaduanFragmentBinding
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasFragmentDirections
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModel
import com.rectangle.cepuonline.ui.home.petugas.dashboard.DashboardPetugasViewModelFactory
import com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.DashboardPetugasAdapter
import com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.listener.DashboardPetugasListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HistoryPengaduanFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: HistoryPengaduanViewModel
    private lateinit var binding : HistoryPengaduanFragmentBinding
    override val kodein by kodein()
    private val viewModelFactory: HistoryPengaduanViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(inflater,R.layout.history_pengaduan_fragment,container,false)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HistoryPengaduanViewModel::class.java)

        viewModel.navigateToDetailPengaduan.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(HistoryPengaduanFragmentDirections.actionHistoryPengaduanFragmentToDetailPengaduanFragment(true,it))
                viewModel.onPengaduanDetailNavigated()
            }

        })

        viewModel.dataUser.observe(viewLifecycleOwner,Observer{
            it?.let {
                it.id?.let { it1 -> viewModel.getPengaduansFromApi(it1) }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val adapter =
            DashboardPetugasAdapter(DashboardPetugasListener {
                viewModel.onPengaduanClicked(it.id)
            })
        binding.feedPengaduanRv.adapter = adapter



        viewModel.listHistoryPengaduan.observe(viewLifecycleOwner, Observer{
            it?.let {
//                Toast.makeText(context, "List refreshed", Toast.LENGTH_SHORT).show()
                adapter.data = it as MutableList<PengaduanResponse>
                adapter.notifyDataSetChanged()
            }
        })
    }

}