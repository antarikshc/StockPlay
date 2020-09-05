package com.antarikshc.stockplay.data

import com.antarikshc.stockplay.data.local.StockDatabase
import com.antarikshc.stockplay.models.Stock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val service: StockService,
    private val db: StockDatabase
) {

    /**
     * Return Flowable Stream of Stocks from Database
     * Automatically emits on each DB update
     */
    fun getStocks(): Flow<List<Stock>> {
        return db.stockDao().getStocks()
    }

    /**
     * Fetch Stock Prices from Socket and Update DB
     * @param coroutineScope = Ideally ViewModelScope
     */
    fun refreshStocks(coroutineScope: CoroutineScope) {
        service.getStonks()
            .onEach {
                db.stockDao().insertOrUpdate(it)
            }
            .launchIn(coroutineScope)
    }

}