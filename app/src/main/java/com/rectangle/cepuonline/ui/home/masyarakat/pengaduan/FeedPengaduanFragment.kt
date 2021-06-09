package com.rectangle.cepuonline.ui.home.masyarakat.pengaduan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rectangle.cepuonline.R
import com.rectangle.cepuonline.databinding.FragmentFeedPengaduanBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

const val EMPTY_ARGS_STRING = "XKOSONGX"
const val EMPTY_ARGS_INT = -1
class FeedPengaduanFragment : Fragment(), KodeinAware{

    private lateinit var viewModel: PengaduanViewModel
    override val kodein by kodein()
    var idMasyarakat : Int? = null
    private  val viewModelFactory: PengaduanViewModelFactory by instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentFeedPengaduanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_feed_pengaduan, container, false)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(PengaduanViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = PengaduanFeedAdapter()
        binding.feedPengaduanRv.adapter = adapter

        viewModel.listPengaduan.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.data = it
            }
            adapter.notifyDataSetChanged()
        })

        viewModel.dataUser.observe(viewLifecycleOwner,Observer{
            it?.let {
                idMasyarakat = it.masyarakat_id
//                Toast.makeText(context, "id masyarakat $it", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton? = view.findViewById(R.id.fabAja)
        fab?.setOnClickListener {
//            Toast.makeText(context, "woah", Toast.LENGTH_SHORT).show()

            activity?.let{
                val intent = Intent (it, AjukanKeluhanActivity::class.java)
                intent.putExtra("masyarakatId",idMasyarakat)
                intent.putExtra("pengaduanId",EMPTY_ARGS_INT)
                intent.putExtra("namaMasyarakat",EMPTY_ARGS_STRING)
                intent.putExtra("subjekPengaduan", EMPTY_ARGS_STRING)
                it.startActivity(intent)
            }
        }
    }
}