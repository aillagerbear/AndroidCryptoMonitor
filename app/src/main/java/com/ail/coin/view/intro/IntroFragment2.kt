package com.ail.coin.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ail.coin.databinding.FragmentIntro2Binding
import com.ail.coin.view.select.SelectActivity


class IntroFragment2 : Fragment() {

    private lateinit var binding: FragmentIntro2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            val intent = Intent(requireContext(), SelectActivity::class.java)
            startActivity(intent)
            onDestroy()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        //In this modification, we use the unbind method of the binding object to unbind
        // it from the fragment's view, which will help prevent memory leaks.
        binding.unbind()
    }

}