package com.ail.coin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interest_coin_table")
class InterestCoinEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val coinName: String,
    val acc_trade_value: String,
    val acc_trade_value_24H: String,
    val closing_price: String,
    val fluctate_24H: String,
    val fluctate_rate_24H: String,
    val max_price: String,
    val min_price: String,
    val opening_price: String,
    val prev_closing_price: String,
    val units_traded: String,
    val units_traded_24H: String,
    val selected: Boolean


)