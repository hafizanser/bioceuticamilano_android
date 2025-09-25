package com.bioceuticamilano.model

import com.google.gson.annotations.SerializedName

// Top-level Card model
data class Card(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("stripe_token") var stripeToken: String? = null,
    @SerializedName("is_default") var isDefault: Boolean? = null,
    @SerializedName("card_details") var cardDetails: CardDetails? = null
)

// Nested CardDetails model
data class CardDetails(
    @SerializedName("id") var id: String? = null,
    @SerializedName("object") var obj: String? = null,
    @SerializedName("address_city") var addressCity: String? = null,
    @SerializedName("address_country") var addressCountry: String? = null,
    @SerializedName("address_line1") var addressLine1: String? = null,
    @SerializedName("address_line1_check") var addressLine1Check: String? = null,
    @SerializedName("address_line2") var addressLine2: String? = null,
    @SerializedName("address_state") var addressState: String? = null,
    @SerializedName("address_zip") var addressZip: String? = null,
    @SerializedName("address_zip_check") var addressZipCheck: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("customer") var customer: String? = null,
    @SerializedName("cvc_check") var cvcCheck: String? = null,
    @SerializedName("dynamic_last4") var dynamicLast4: String? = null,
    @SerializedName("exp_month") var expMonth: Int? = null,
    @SerializedName("exp_year") var expYear: String? = null,
    @SerializedName("fingerprint") var fingerprint: String? = null,
    @SerializedName("funding") var funding: String? = null,
    @SerializedName("last4") var last4: String? = null,
    @SerializedName("metadata") var metadata: List<Any>? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("tokenization_method") var tokenizationMethod: String? = null
)