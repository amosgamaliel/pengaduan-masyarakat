package com.rectangle.cepuonline.ui.home.ajukan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rectangle.cepuonline.databinding.FragmentAjukanKeluhanBinding
import com.rectangle.cepuonline.R
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AjukanKeluhanFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: AjukanKeluhanViewModel
    override val kodein by kodein()
    private val viewModelFactory: AjukanKeluhanViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding : FragmentAjukanKeluhanBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_ajukan_keluhan,container,false)
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(AjukanKeluhanViewModel::class.java)
        return binding.root
    }


}