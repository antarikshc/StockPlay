package com.antarikshc.stockplay.data.remote

import com.antarikshc.stockplay.helpers.Socket
import com.antarikshc.stockplay.models.IncPrices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.round

@Singleton
class StockService @Inject constructor(private val socket: Socket, private val gson: Gson) {

    companion object {
        const val URL = "ws://stocks.mnet.website"
    }

    fun getStonks(): Flow<List<IncPrices>> {
        return socket.connect(URL)
            .map {
                gson.fromJson(it, object : TypeToken<List<IncPrices>>() {}.type) as List<IncPrices>
            }
            .map {
                it.map { item ->
                    item.copy(price = round(item.price * 1000) / 1000)
                }
            }
            .flowOn(Dispatchers.IO)
    }

}