package com.ail.coin.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ail.coin.R
import com.ail.coin.databinding.FragmentCoinListBinding
import com.ail.coin.databinding.FragmentPriceChangeBinding

class PriceChangeFragment : Fragment() {

    private lateinit var binding: FragmentPriceChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPriceChangeBinding.inflate(inflater, container, false)
        return binding.root
    }


}