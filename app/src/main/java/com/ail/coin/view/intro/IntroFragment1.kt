package com.ail.coin.view.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ail.coin.R
import com.ail.coin.databinding.FragmentIntro1Binding

class IntroFragment1 : Fragment() {

    private lateinit var binding: FragmentIntro1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntro1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_introFragment1_to_introFragment2)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        //In this modification, we use the unbind method of the binding object to unbind
        // it from the fragment's view, which will help prevent memory leaks.
        binding.unbind()
    }

}