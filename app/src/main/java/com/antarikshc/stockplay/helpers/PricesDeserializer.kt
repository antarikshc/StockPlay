package com.antarikshc.stockplay.helpers

import com.antarikshc.stockplay.models.IncPrices
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PricesDeserializer : JsonDeserializer<IncPrices> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): IncPrices {
        // Example Input:
        // [
        //      ["intc",147.8571865982666],
        //      ["tck",37.09782037661585],
        //      ["ebr",160.86492026172508]
        // ]

        // Get both elements manually and create IncPrices

        val array = json?.asJsonArray
        return IncPrices(array?.get(0)?.asString ?: "MSFT", array?.get(1)?.asDouble ?: 0.0)
    }


}