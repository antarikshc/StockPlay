package com.antarikshc.stockplay.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class Stock(

    @PrimaryKey(autoGenerate = false)
    val name: String,

    val price: Double,

    @ColumnInfo(name = "previous_price")
    val previousPrice: Double = 0.0,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()

)