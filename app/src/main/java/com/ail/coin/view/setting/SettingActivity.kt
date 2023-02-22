package com.ail.coin.view.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ail.coin.databinding.ActivitySettingBinding
import com.ail.coin.service.PriceForegroundService
import timber.log.Timber

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    var notiBoolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startForeground.setOnClickListener {

            Toast.makeText(this, "start", Toast.LENGTH_LONG).show()

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var myNotificationChannel: NotificationChannel? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !notiBoolean) {
                val channel = NotificationChannel(
                    "coin_channel_id",
                    "Coin",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
                val channelId =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) channel.id else "coin_channel_id"
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                notiBoolean = true
                Timber.d(notiBoolean.toString())
                startActivity(intent)
            }

            val channelNotificationSettingsReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == "REQUEST_CHANNEL_NOTIFICATION_SETTINGS" && !notiBoolean) {
                        val channelIntent =
                            Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                                putExtra(Settings.EXTRA_CHANNEL_ID, myNotificationChannel?.id)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        notiBoolean = true
                        Timber.d(notiBoolean.toString())
                        startActivity(channelIntent)
                    }
                }
            }

            registerReceiver(
                channelNotificationSettingsReceiver,
                IntentFilter("REQUEST_CHANNEL_NOTIFICATION_SETTINGS")
            )

            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "START"
            startService(intent)
        }

        binding.stopForeground.setOnClickListener {
            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            notiBoolean = false
            startService(intent)
        }
    }
}