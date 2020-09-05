package com.antarikshc.stockplay.data

import com.antarikshc.stockplay.models.IncPrices
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val service: StockService) {

    fun getStock(): Flow<List<IncPrices>> {
        return service.getStonks()
    }

}