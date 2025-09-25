package com.bioceuticamilano.utils

import android.content.Context
import com.bioceuticamilano.model.CarDetailModel

// 1. Get price code from city
fun String.getPriceCodeWithCity(context: Context): String {
    val priceCodes = mapOf(
        "Atlanta" to "price_2",
        "Boston" to "price_3",
        "Charleston" to "price_4",
        "Birmingham" to "price_5",
        "Charlotte" to "price_6",
        "Connecticut" to "price_7",
        "Las Vegas" to "price_8",
        "Los Angeles" to "price_9",
        "Miami" to "price_10",
        "Nashville" to "price_11",
        "New Jersey" to "price_12",
        "New York" to "price_13",
        "Orlando" to "price_14",
        "Rhode Island" to "price_15",
        "San Diego" to "price_16",
        "San Francisco" to "price_17",
        "Tampa" to "price_18",
        "UK" to "price_19",
        "Dubai" to "price_20",
        "Alabama" to "price_2",
        "California" to "price_9",
        "Florida" to "price_9",
        "Georgia" to "price_2",
        "Massachusetts" to "price_2",
        "Nevada" to "price_2",
        "North Carolina" to "price_2",
        "South Carolina" to "price_2",
        "Tennessee" to "price_2"
    )
    val formattedCity = this.trim().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val cityPriceCode = priceCodes[formattedCity] ?: "price_20"
    Preferences.saveSharedPrefValue(context, "currentPriceCode", cityPriceCode)
    return cityPriceCode
}

// 2. Get per-day rent from price code (for CarDetailModel)
fun getRentPerDay(
    priceCode: String,
    dataModel: CarDetailModel,
    currencySign: String,
): String {
    // Each branch may return an Int?; avoid forcing null with !! and handle null safely
    val candidate: Int? = when (priceCode) {
        "price_1" -> dataModel.data?.price1
        "price_2" -> dataModel.data?.price2
        "price_3" -> dataModel.data?.price3
        "price_4" -> dataModel.data?.price4
        "price_5" -> dataModel.data?.price5
        "price_6" -> dataModel.data?.price6
        "price_7" -> dataModel.data?.price7
        "price_8" -> dataModel.data?.price8
        "price_9" -> dataModel.data?.price9
        "price_10" -> dataModel.data?.price10
        "price_11" -> dataModel.data?.price11
        "price_12" -> dataModel.data?.price12
        "price_13" -> dataModel.data?.price13
        "price_14" -> dataModel.data?.price14
        "price_15" -> dataModel.data?.price15
        "price_16" -> dataModel.data?.price16
        "price_17" -> dataModel.data?.price17
        "price_18" -> dataModel.data?.price18
        "price_19" -> dataModel.data?.price19
        "price_20" -> dataModel.data?.price20
        else -> null
    }

    // Prefer a positive candidate price; otherwise fall back to generic price or 0
    val perDayRentInt: Int = candidate?.takeIf { it > 0 } ?: (dataModel.data?.price ?: 0)

    val finalTripPrice = "$currencySign$perDayRentInt/Per day"

    return finalTripPrice
}
