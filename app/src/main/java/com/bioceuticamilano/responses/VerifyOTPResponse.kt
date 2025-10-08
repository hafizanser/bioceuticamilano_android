package com.bioceuticamilano.responses

import com.google.gson.annotations.SerializedName

data class VerifyOTPResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Data(

	@field:SerializedName("stripe_customer_id")
	val stripeCustomerId: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
