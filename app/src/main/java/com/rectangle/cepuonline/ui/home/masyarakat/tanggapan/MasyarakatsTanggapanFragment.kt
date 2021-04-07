package com.rectangle.cepuonline.ui.home.masyarakat.tanggapan

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
import com.rectangle.cepuonline.databinding.MasyarakatsTanggapanFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MasyarakatsTanggapanFragment : Fragment(),KodeinAware {

    private lateinit var viewModel: MasyarakatsTanggapanViewModel
    private lateinit var binding : MasyarakatsTanggapanFragmentBinding
    override val kodein by kodein()
    private val viewModelFactory: MasyarakatsTanggapanViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = DataBindingUtil.inflate(inflater,R.layout.masyarakats_tanggapan_fragment,container,false)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(MasyarakatsTanggapanViewModel::class.java)

        viewModel.navigateToDetailTanggapan.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(MasyarakatsTanggapanFragmentDirections.actionMasyarakatsTanggapanFragmentToDetailTanggapanFragment(0))
                viewModel.onTanggapanDetailNavigated()
            }

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val adapter =
            MasyarakatTanggapansAdapter(TanggapanListener {
                viewModel.onTanggapanClicked(it.id)
            })
        binding.feedPengaduanRv.adapter = adapter

        viewModel.listTanggapan.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.data = it
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.dataUser.observe(viewLifecycleOwner,Observer{
            it?.let {
//                Toast.makeText(context, "user ada", Toast.LENGTH_SHORT).show()
                it.masyarakat_id?.let { it1 -> viewModel.getTanggapansFromApi(it1) }
            }
        })
    }

}