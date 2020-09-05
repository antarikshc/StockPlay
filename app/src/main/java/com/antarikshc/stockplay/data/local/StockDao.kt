package com.antarikshc.stockplay.data.local

import androidx.room.*
import com.antarikshc.stockplay.models.IncPrices
import com.antarikshc.stockplay.models.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {


    @Query("SELECT * FROM stock_table WHERE name = :name")
    suspend fun get(name: String): Stock?


    // Limit return values till Paging
    @Query("SELECT * FROM stock_table ORDER BY updated_at DESC LIMIT 50")
    fun getStocks(): Flow<List<Stock>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: Stock)


    /**
     * Updates stock prices if already present in DB
     * Inserts if no stock present (prev price = 0)
     */
    @Transaction
    suspend fun insertOrUpdate(prices: List<IncPrices>) {
        prices.forEach { item ->
            // Get Stock from DB
            val stockFromDb = get(item.name)

            val stockToInsert = if (stockFromDb != null) {
                // Update previous price
                Stock(
                    name = item.name,
                    price = item.price,
                    previousPrice = stockFromDb.price
                )
            } else {
                Stock(
                    name = item.name,
                    price = item.price
                )
            }

            insert(stockToInsert)
        }
    }


}