package com.bioceuticamilano.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class CarDetailModel(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("price_14")
	val price14: Int? = null,

	@field:SerializedName("price_13")
	val price13: Int? = null,

	@field:SerializedName("price_16")
	val price16: Int? = null,

	@field:SerializedName("price_1")
	val price1: Int? = null,

	@field:SerializedName("price_15")
	val price15: Int? = null,

	@field:SerializedName("price_2")
	val price2: Int? = null,

	@field:SerializedName("price_18")
	val price18: Int? = null,

	@field:SerializedName("price_3")
	val price3: Int? = null,

	@field:SerializedName("price_17")
	val price17: Int? = null,

	@field:SerializedName("price_4")
	val price4: Int? = null,

	@field:SerializedName("available_cities")
	val availableCities: List<AvailableCitiesItem?>? = null,

	@field:SerializedName("price_5")
	val price5: Int? = null,

	@field:SerializedName("price_19")
	val price19: Int? = null,

	@field:SerializedName("price_10")
	val price10: Int? = null,

	@field:SerializedName("seats")
	val seats: String? = null,

	@field:SerializedName("price_12")
	val price12: Int? = null,

	@field:SerializedName("price_11")
	val price11: Int? = null,

	@field:SerializedName("transmission")
	val transmission: String? = null,

	@field:SerializedName("pickup_fees")
	val pickupFees: Int? = null,

	@field:SerializedName("is_like")
	var isLike: Boolean? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("body_type")
	val bodyType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fuel_type")
	val fuelType: String? = null,

	@field:SerializedName("brand")
	val brand: Brand? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("car_color")
	val carColor: String? = null,

	@field:SerializedName("price_21")
	val price21: Int? = null,

	@field:SerializedName("price_20")
	val price20: Int? = null,

	@field:SerializedName("last_trip")
	val lastTrip: String? = null,

	@field:SerializedName("fuel_fees")
	val fuelFees: Int? = null,

	@field:SerializedName("airports")
	val airports: List<AirportsItem?>? = null,

	@field:SerializedName("doors")
	val doors: String? = null,

	@field:SerializedName("delivery_fees")
	val deliveryFees: Int? = null,

	@field:SerializedName("dropoff_fees")
	val dropoffFees: Int? = null,

	@field:SerializedName("insurance_fees")
	val insuranceFees: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("destination")
	val destination: Destination? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("interior")
	val interior: String? = null,

	@field:SerializedName("pickupanddropoff_fees")
	val pickupanddropoffFees: Int? = null,

	@field:SerializedName("engine")
	val engine: String? = null,

	@field:SerializedName("car_model")
	val carModel: String? = null,

	@field:SerializedName("test_drive_price")
	val testDrivePrice: Int? = null,

	@field:SerializedName("totat_stars")
	val totatStars: Float? = null,

	@field:SerializedName("totat_trips")
	val totatTrips: Int? = null,

	@field:SerializedName("seating_capacity")
	val seatingCapacity: Int? = null,

	@field:SerializedName("car_number")
	val carNumber: String? = null,

	@field:SerializedName("price_6")
	val price6: Int? = null,

	@field:SerializedName("price_7")
	val price7: Int? = null,

	@field:SerializedName("price_8")
	val price8: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("current_price")
	val currentPrice: Int? = null,

	@field:SerializedName("price_9")
	val price9: Int? = null,

	@field:SerializedName("category")
	val category: Category? = null
) : Parcelable
@Parcelize
data class Brand(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
@Parcelize
data class Destination(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
@Parcelize
data class ImagesItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("item_id")
	val itemId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
@Parcelize
data class AirportsItem(

	@field:SerializedName("fees")
	val fees: Int? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("lng")
	val lng: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("tax")
	val tax: String? = null,

	@field:SerializedName("state_initial")
	val stateInitial: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("is_office_exit")
	val isOfficeExit: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null,

	@field:SerializedName("lat")
	val lat: String? = null
) : Parcelable
@Parcelize
data class Category(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
@Parcelize
data class AvailableCitiesItem(

	@field:SerializedName("own_address_extra_fees")
	val ownAddressExtraFees: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("lng")
	val lng: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("tax")
	val tax: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
) : Parcelable
