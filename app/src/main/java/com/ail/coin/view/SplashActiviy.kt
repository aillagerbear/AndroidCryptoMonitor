package com.ail.coin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.ail.coin.MainActivity
import com.ail.coin.R
import com.ail.coin.databinding.ActivitySplashBinding
import com.ail.coin.view.intro.IntroViewModel
import timber.log.Timber

class SplashActiviy : AppCompatActivity() {

    private val viewModel: IntroViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.animeSplash.playAnimation()

        binding.fragmentContainerView.visibility = if (viewModel.first.value == false) {
            View.VISIBLE
        } else {
            View.GONE
        }

        viewModel.checkFirstFlag()


        viewModel.first.observe(this) {
            Timber.d(it.toString())
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.fragmentContainerView.visibility = View.VISIBLE
                binding.animeSplash.visibility = View.INVISIBLE
            }
        }
    }
}