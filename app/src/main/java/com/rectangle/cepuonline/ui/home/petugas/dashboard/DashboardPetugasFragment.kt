package com.rectangle.cepuonline.ui.home.petugas.dashboard

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
import com.rectangle.cepuonline.databinding.FragmentDashboardPetugasBinding
import com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.DashboardPetugasAdapter
import com.rectangle.cepuonline.ui.home.petugas.dashboard.adapter.listener.DashboardPetugasListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DashboardPetugasFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: DashboardPetugasViewModel
    private lateinit var binding : FragmentDashboardPetugasBinding
    override val kodein by kodein()
    private val viewModelFactory: DashboardPetugasViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard_petugas,container,false)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(DashboardPetugasViewModel::class.java)

        viewModel.navigateToDetailPengaduan.observe(viewLifecycleOwner,Observer{
            it?.let {
                this.findNavController().navigate(DashboardPetugasFragmentDirections.actionDashboardPetugasFragmentToDetailPengaduanFragment())
                viewModel.onPengaduanDetailNavigated()
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
        adapter.removeItem()
        viewModel.refreshData()
        binding.feedPengaduanRv.adapter = adapter

        viewModel.listPengaduan.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.data = it as MutableList<PengaduanResponse>
                adapter.notifyDataSetChanged()
            }
        })
    }
}