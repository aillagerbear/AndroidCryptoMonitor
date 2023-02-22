package com.ail.coin.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ail.coin.R
import com.ail.coin.databinding.FragmentCoinListBinding
import com.ail.coin.databinding.FragmentPriceChangeBinding
import com.ail.coin.view.adpapter.PriceListUpDownRVAdapter
import timber.log.Timber

class PriceChangeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentPriceChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPriceChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllSelectedCoinData()

        viewModel.arr15min.observe(viewLifecycleOwner, Observer {
            Timber.tag("데이터15분").d(it.toString())

            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price15m.adapter = priceListUpDownRVAdapter
            binding.price15m.layoutManager = LinearLayoutManager(requireContext())

        })

        viewModel.arr30min.observe(viewLifecycleOwner, Observer {
            Timber.tag("데이터30분").d(it.toString())

            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price30m.adapter = priceListUpDownRVAdapter
            binding.price30m.layoutManager = LinearLayoutManager(requireContext())
        })

        viewModel.arr45min.observe(viewLifecycleOwner, Observer {
            Timber.tag("데이터45분").d(it.toString())

            val priceListUpDownRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price45m.adapter = priceListUpDownRVAdapter
            binding.price45m.layoutManager = LinearLayoutManager(requireContext())
        })

    }


}