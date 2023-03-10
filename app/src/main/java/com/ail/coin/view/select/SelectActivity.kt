package com.ail.coin.view.select

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ail.coin.view.main.MainActivity
import com.ail.coin.databinding.ActivitySelectBinding
import com.ail.coin.view.adpapter.SelectRVAdapter
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ail.coin.background.GetCoinPriceRecentContractedWorkManager
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private val viewModel: SelectViewModel by viewModels()
    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectRVAdapter = SelectRVAdapter(this, listOf()) // create the adapter with an empty list

        binding.coinListRV.adapter = selectRVAdapter // set the adapter to the RecyclerView

        viewModel.getCurrnetCoinList()
        viewModel.currentPriceResult.observe(this) { result ->
            selectRVAdapter.updateData(result) // update the adapter with the new data
        }



        binding.laterTextArea.setOnClickListener {
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer {
            if (it.equals("done")) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                saveInterestCoinDataPeriodic()

            }
        })
    }


    private fun saveInterestCoinDataPeriodic() {

        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )

    }

}
