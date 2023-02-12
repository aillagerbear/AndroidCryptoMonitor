package com.ail.coin.view.select

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ail.coin.R
import com.ail.coin.databinding.ActivitySelectBinding
import com.ail.coin.databinding.ActivitySplashBinding
import timber.log.Timber

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private val viewModel : SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrnetCoinList()

    }
}