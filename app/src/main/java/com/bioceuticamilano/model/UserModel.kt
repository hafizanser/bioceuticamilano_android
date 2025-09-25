package com.bioceuticamilano.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("is_host")
    @Expose
    var isHost: Int? = null

    @SerializedName("full_name")
    @Expose
    var fullName: String? = null

    @SerializedName("country_code")
    @Expose
    var country_code : String? = null

    @SerializedName("country_symbol")
    @Expose
    var country_symbol : String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("call_status")
    @Expose
    var callStatus: Int? = null

    @SerializedName("country_id")
    @Expose
    var countryId: Int? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("profile_type")
    @Expose
    var profileType: String? = null

    @SerializedName("age")
    @Expose
    var age: Int? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    @SerializedName("front_license_url")
    @Expose
    var frontLicenseUrl: String? = null

    @SerializedName("back_license_image")
    @Expose
    var backLicenseImage: String? = null

    @SerializedName("upload_insurance_document_image")
    @Expose
    var uploadInsuranceDocumentImage: String? = null

    @SerializedName("user_type")
    @Expose
    var userType: String? = null

    @SerializedName("stripe_customer_id")
    @Expose
    var stripeCustomerId: String? = null

    @SerializedName("destination_id")
    @Expose
    var destinationId: Int? = null

    @SerializedName("conversation_id")
    @Expose
    var conversationId: String? = null

    @SerializedName("device_type")
    @Expose
    var deviceType: String? = null

    @SerializedName("device_token")
    @Expose
    var deviceToken: String? = null

    @SerializedName("vehicle_count")
    @Expose
    var vehicleCount: String? = null

    @SerializedName("cities")
    @Expose
    var cities: String? = null

    @SerializedName("vehicle_types")
    @Expose
    var vehicleTypes: String? = null

    @SerializedName("ownership_type")
    @Expose
    var ownershipType: String? = null

    @SerializedName("has_rental_experience")
    @Expose
    var hasRentalExperience: String? = null

    @SerializedName("rental_platforms")
    @Expose
    var rentalPlatforms: String? = null

    @SerializedName("rental_count")
    @Expose
    var rentalCount: String? = null

    @SerializedName("has_valid_registration_insurance")
    @Expose
    var hasValidRegistrationInsurance: String? = null

    @SerializedName("interested_in_commercial_insurance")
    @Expose
    var interestedInCommercialInsurance: String? = null

    @SerializedName("has_license_and_docs")
    @Expose
    var hasLicenseAndDocs: String? = null

    @SerializedName("rental_management")
    @Expose
    var rentalManagement: String? = null

    @SerializedName("has_pickup_location")
    @Expose
    var hasPickupLocation: String? = null

    @SerializedName("delivery_preference")
    @Expose
    var deliveryPreference: String? = null

    @SerializedName("interested_in_fyv_storage")
    @Expose
    var interestedInFyvStorage: String? = null

    @SerializedName("vehicle_availability")
    @Expose
    var vehicleAvailability: String? = null

    @SerializedName("fyv_goals")
    @Expose
    var fyvGoals: String? = null

    @SerializedName("referral_source")
    @Expose
    var referralSource: String? = null

    @SerializedName("referral_name")
    @Expose
    var referralName: String? = null

    @SerializedName("other_referral_detail")
    @Expose
    var otherReferralDetail: String? = null

    @SerializedName("additional_notes")
    @Expose
    var additionalNotes: String? = null

    @SerializedName("driver_license_file")
    @Expose
    var driverLicenseFile: String? = null

    @SerializedName("registration_files")
    @Expose
    var registrationFiles: String? = null

    @SerializedName("insurance_proof_file")
    @Expose
    var insuranceProofFile: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("is_profile_complete")
    @Expose
    var isProfileComplete: Int? = null

    @SerializedName("isProfileComplete")
    @Expose
    var isProfileCompleteString: String? = null

    @SerializedName("number_of_trips")
    @Expose
    var numberOfTrips: Int? = null

    @SerializedName("fav_vehicles")
    @Expose
    var favVehicles: Int? = null

    @SerializedName("status")
    @Expose
    var status: Boolean? = null

    @SerializedName("is_admin")
    @Expose
    var isAdmin: Boolean? = null

    @SerializedName("default_card")
    @Expose
    var defaultCard: DefaultCard? = null

    @SerializedName("social_login")
    @Expose
    var socialLogin: String? = null

    // Getters and Setters for UserModel
    var authToken: String? = "Bearer 107|4NNM9VUiYlJkyvwga1hSVBLOwakdi9ZhylQMzsAw"

    // Default Card inner class
    class DefaultCard {
        // Getters and Setters for DefaultCard
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("exp")
        @Expose
        var exp: String? = null

        @SerializedName("exp_year")
        @Expose
        var expYear: String? = null

        @SerializedName("exp_month")
        @Expose
        var expMonth: String? = null

        @SerializedName("last4")
        @Expose
        var last4: String? = null

        @SerializedName("brand")
        @Expose
        var brand: String? = null

        @SerializedName("cvv")
        @Expose
        var cvv: String? = null

        @SerializedName("is_default")
        @Expose
        var isDefault: Boolean? = null

        @SerializedName("card_details")
        @Expose
        var cardDetails: CardDetails? = null
    }

    // Card Details inner class
    class CardDetails {
        // Getters and Setters for CardDetails
        @SerializedName("brand")
        @Expose
        var brand: String? = null

        @SerializedName("cvv")
        @Expose
        var cvv: String? = null

        @SerializedName("exp")
        @Expose
        var exp: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("lastname")
        @Expose
        var lastname: String? = null

        @SerializedName("firstname")
        @Expose
        var firstname: String? = null

        @SerializedName("card_details")
        @Expose
        var cardDetails: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: Int? = null

        @SerializedName("is_default")
        @Expose
        var isDefault: Boolean? = null

        @SerializedName("last4")
        @Expose
        var last4: String? = null

        @SerializedName("exp_month")
        @Expose
        var expMonth: String? = null

        @SerializedName("exp_year")
        @Expose
        var expYear: String? = null
    }
}
