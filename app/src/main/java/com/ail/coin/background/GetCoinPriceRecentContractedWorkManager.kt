package com.ail.coin.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ail.coin.service.Repository.DBRepository
import com.ail.coin.service.Repository.NetWorkRepository
import com.ail.coin.model.RecentCoinPriceList
import com.ail.coin.view.db.entity.SelectedCoinPriceEntity
import timber.log.Timber
import java.util.*

class GetCoinPriceRecentContractedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {

        Timber.d("doWorkd")

        getAllInterestSelectedCoinData()

        return Result.success()
    }

    suspend fun getAllInterestSelectedCoinData() {

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {
            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coinName)

            saveSelectedCoinPrice(
                coinData.coinName,
                recentCoinPriceList,
                timeStamp

            )
        }

    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp: Date
    ) {

        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)

    }

}