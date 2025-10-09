package com.bioceuticamilano.responses

import com.google.gson.annotations.SerializedName

data class GetAddressResponse(

	@field:SerializedName("data")
	val data: DataA? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataA(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("full_address")
	val fullAddress: String? = null,

	@field:SerializedName("location_tag")
	val locationTag: String? = null,

	@field:SerializedName("is_default")
	val isDefault: Int? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null
)
