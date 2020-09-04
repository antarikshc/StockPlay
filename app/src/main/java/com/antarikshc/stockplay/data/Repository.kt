package com.antarikshc.stockplay.data

import com.antarikshc.stockplay.models.IncPrices
import kotlinx.coroutines.flow.Flow

class Repository(private val service: StockService) {


    fun getStock(): Flow<List<IncPrices>> {
        return service.getStonks()
    }

}