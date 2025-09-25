package com.bioceuticamilano.network;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiCall {

    @Multipart
    @POST("signup")
    Observable<Object> register(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("update-user")
    Observable<Object> updateProfile(@Header("Authorization") String Token,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("log-in")
    Observable<Object> login(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("create-trip")
    Observable<Object> createTrip(@Header("Authorization") String Token,@PartMap Map<String, RequestBody> params);

//    @POST("create-trip")
//    Observable<Object> createTripWithModel(@Header("Authorization") String Token, @Body app.carrental.models.TripRequestModel requestModel);


    @Multipart
    @POST("order")
    Observable<Object> placeOrder(@Header("Authorization") String Token,@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("social-login")
    Observable<Object> socialLogin(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/add-travel")
    Observable<Object> addTravelForm(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);

    @Multipart
    @POST("api/add-hotel")
    Observable<Object> addHotelForm(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);

    @Multipart
    @POST("api/add-recovery")
    Observable<Object> addTaxPayer(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/add-lesisure")
    Observable<Object> addLeisureForm(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params,@Part MultipartBody.Part part);

    @Multipart
    @POST("api/add-restaurant")
    Observable<Object> addRestaurantForm(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);

    @Multipart
    @POST("vehicle-like")
    Observable<Object> likePost(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/post-save")
    Observable<Object> savePost(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/favourite-location")
    Observable<Object> addFavCity(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/follow")
    Observable<Object> follow(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/add-post")
    Observable<Object> addPost(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);


    @Multipart
    @POST("verify-otp")
    Observable<Object> verifyOtp(@PartMap Map<String, RequestBody> params);



    @Multipart
    @POST("resend-otp")
    Observable<Object> resendOtp(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("password/email")
    Observable<Object> forgotPasswors(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/forgotPassword")
    Observable<Object> forgotPassword(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/send-otp")
    Observable<Object> sendOtp(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("reset-password")
    Observable<Object> changePassword(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @GET("get-horoscope")
    Observable<Object> getQuote(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @Multipart
    @POST("cancel-trip")
    Observable<Object> cancelTrip(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @GET("get-trip")
    Observable<Object> getTripDetails(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("filter-vehicles")
    Observable<Object> getFilterResult(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("get-vehicle")
    Observable<Object> getVehicleDetails(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @Multipart
    @POST("create-credit-card")
    Observable<Object> createCreditCard(
        @Header("Authorization") String Token,
        @PartMap Map<String, RequestBody> params
    );

    @GET("api/get-taxpayers")
    Observable<Object> getTaxPayers(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/get-taxpayers-list")
    Observable<Object> getTaxPayersData(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/get-invoices")
    Observable<Object> getInvoices(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/files/{url}")
    Observable<Object> getSinglePost(@Header("Authorization") String Token, @Path(value = "url", encoded = true) String url, @QueryMap Map<String, String> params);

    @Multipart
    @POST("api/files-uploading")
    Observable<Object> uploadFile(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);


    @Multipart
    @POST("api/create-folder")
    Observable<Object> createFolder(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/move/{url}")
    Observable<Object> moveTo(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Path(value = "url", encoded = true) String url);

    @Multipart
    @POST("api/add-post")
    Observable<Object> uploadVideoPost(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part Video_part, @Part MultipartBody.Part part);

    @GET("api/comment-listing-post")
    Observable<Object> getComments(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/comment-listing")
    Observable<Object> getRestaurantsComments(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @Multipart
    @POST("api/add-qrcode")
    Observable<Object> addQr(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part imagePart);


    @Multipart
    @POST("api/add-comment")
    Observable<Object> addRestaurantComment(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/restaurant-like")
    Observable<Object> restaurantLike(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/restaurant-comment-like")
    Observable<Object> likeCommentRestaurant(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/post-comment-like")
    Observable<Object> likeComment(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/add-post-comment-reply")
    Observable<Object> addReply(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/add-restaurant-comment-reply")
    Observable<Object> addRestaurantReply(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/add-post")
    Observable<Object> uploadImagePost(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);


    @Multipart
    @POST("update-user")
    Observable<Object> save_profile(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("update-user")
    Observable<Object> updateProfilePicture(@Header("Authorization") String Token, @Part MultipartBody.Part part);

    @Multipart
    @POST("vehicle")
    Observable<Object> addVehicle(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);

    @Multipart
    @POST("address")
    Observable<Object> addAddress(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("address/{url}")
    Observable<Object> editAddress(@Header("Authorization") String Token,@Path("url") String path, @PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("vehicle/{url}")
    Observable<Object> editVehicle(@Header("Authorization") String Token,@Path("url") String path, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part part);

    @HTTP(method = "DELETE", path = "vehicle/{url}", hasBody = true)
    Observable<Object> deleteVehicle(@Header("Authorization") String Token,@Path("url") String path);

    @HTTP(method = "DELETE", path = "credit-card/{url}", hasBody = true)
    Observable<Object> deleteCard(@Header("Authorization") String Token,@Path("url") String path);

    @GET("get-credit-card/{url}/set-default")
    Observable<Object> makeDefault(@Header("Authorization") String Token,@Path("url") String path);

    @HTTP(method = "DELETE", path = "address/{url}", hasBody = true)
    Observable<Object> deleteAddress(@Header("Authorization") String Token,@Path("url") String path);


    @Multipart
    @POST("api/verifyOTP")
    Observable<Object> verifyCode(@PartMap Map<String, RequestBody> params);




    @Multipart
    @POST("admin/order-finish")
    Observable<Object> finishOrder(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params, @Part ArrayList<MultipartBody.Part> part,@Part ArrayList<MultipartBody.Part> videoPart);



    @POST("logout")
    Observable<Object> logout(@Header("Authorization") String Token);

    @Multipart
    @POST("api/follow")
    Observable<Object> followUnfollow(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);


    @GET("api/remove-follower")
    Observable<Object> removeFollowers(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @Multipart
    @POST("api/post-like")
    Observable<Object> postLike(@Header("Authorization") String Token, @PartMap Map<String, RequestBody> params);

    @GET("api/user-follower-list")
    Observable<Object> getFollowers(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/user-following-list")
    Observable<Object> getFollowing(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("favourite-vehicles")
    Observable<Object> getLikes(@Header("Authorization") String Token);

    @GET("api/user-info")
    Observable<Object> getProfile(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/user-trending")
    Observable<Object> getTrendingUser(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/resturant-info")
    Observable<Object> getRestaurant(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/user-search")
    Observable<Object> getSearchData(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/search")
    Observable<Object> searchAllData(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("vehicle/{url}")
    Observable<Object> getVehicleDetails(@Header("Authorization") String Token,@Path("url")String url);




    @GET("order/{url}/update-status")
    Observable<Object> cancelOrder(@Header("Authorization") String Token,@Path("url")String url,@QueryMap Map<String, String> params);

    @GET("order/{url}")
    Observable<Object> getOrderDetails(@Header("Authorization") String Token,@Path("url")String url);


    @GET("address/{url}")
    Observable<Object> getLocationDetails(@Header("Authorization") String Token,@Path("url")String url);

    @GET("order")
    Observable<Object> getOrders(@Header("Authorization") String Token);

    @GET("user-notification")
    Observable<Object> getNotifications(@Header("Authorization") String Token);

    @GET("admin/orders-history")
    Observable<Object> getAdminOrders(@Header("Authorization") String Token);


    @GET("admin/orders-status")
    Observable<Object> getAllOrders(@Header("Authorization") String Token);


    @GET("address")
    Observable<Object> getLocations(@Header("Authorization") String Token);

    @GET("credit-card")
    Observable<Object> getCreditCard(@Header("Authorization") String Token);

    @GET("get-credit-card")
    Observable<Object> getAllCreditCards(@Header("Authorization") String Token);

    @GET("vehicle")
    Observable<Object> getVehicleDetails(@Header("Authorization") String Token);


//    @GET("vehicles-by-ID")
//    Observable<Object> getVehicles(@Header("Authorization") String Token,@QueryMap Map<String, String> params);


    @GET("get-user-upcoming-trips")
    Observable<Object> getUpcomingTrips(@Header("Authorization") String Token);

    @GET("credit-card")
    Observable<Object> getCards(@Header("Authorization") String Token);

    @DELETE("delete-credit-card/{url}")
    Observable<Object> deleteCards(@Header("Authorization") String Token,@Path("url") String url);

    @GET("get-user-past-trips")
    Observable<Object> getPastTrips(@Header("Authorization") String Token);

    @GET("api/getRestaurantsByFoodCategoryId")
    Observable<Object> getRestaurantsByFoodCategoryId(@QueryMap Map<String, String> params);

    @GET("api/getRestaurnatDetails")
    Observable<Object> getRestaurnatDetails(@QueryMap Map<String, String> params);

    @GET("api/getFavoritesRestaurants")
    Observable<Object> getFavoritesRestaurants(@QueryMap Map<String, String> params);

    @Multipart
    @POST("api/contactUs")
    Observable<Object> contactUs(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/cancelOrder")
    Observable<Object> cancelOrder(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/createOrder")
    Observable<Object> createOrder(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/saveAddress")
    Observable<Object> saveAddress(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/resetNotificationBadge")
    Observable<Object> resetNotificationBadge(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/addReview")
    Observable<Object> addReview(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/markFavoriteUnFavorite")
    Observable<Object> markFavoriteUnFavorite(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/addPaymentCard")
    Observable<Object> AddCard(@PartMap Map<String, RequestBody> params);

//    @FormUrlEncoded
//    @POST("api/addReview")
//    Call<OrderDetailsAPIModel> addReviews(
//            @Field("auth_token") String auth_token,
//            @Field("restaurantId") String restaurantId,
//            @Field("review") String review,
//            @Field("rating") String rating,
//            @Field("orderId") String orderId
//    );


    @Multipart
    @POST("api/createAppointment")
    Observable<Object> createAppointment(@PartMap Map<String, RequestBody> params);

    @GET("api/get-tag")
    Observable<Object> getTags(@Header("Authorization") String Token);

    @GET("api/resturant-listing")
    Observable<Object> getRestaurants(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/post-tag")
    Observable<Object> getPostsFromTags(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/post-location")
    Observable<Object> getPostsFromCities(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/post-listing")
    Observable<Object> getPostListing(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/post-trending")
    Observable<Object> getTrendingPosts(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @GET("api/location-listing")
    Observable<Object> getMyListHome(@Header("Authorization") String Token, @QueryMap Map<String, String> params);


    @GET("api/post-location-info")
    Observable<Object> getRestaurantsOfCity(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @Multipart
    @POST("api/cancelAppointment")
    Observable<Object> cancelAppointment(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/editAppointment")
    Observable<Object> editAppointment(@PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/applyDiscountCode")
    Observable<Object> apply_code(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/redeemReward")
    Observable<Object> redeemReward(@PartMap Map<String, RequestBody> params);

    @GET("api/getDiscountsPromoList")
    Observable<Object> getcodes(@QueryMap Map<String, String> params);


    @GET("api/getNotifications")
    Observable<Object> getNotifications(@QueryMap Map<String, String> params);

    @GET("api/getMyAddresses")
    Observable<Object> getMyAddresses(@QueryMap Map<String, String> params);

    @GET("api/getOrderDetails")
    Observable<Object> getOrderDetails(@QueryMap Map<String, String> params);

    @GET("api/getMyOrders")
    Observable<Object> getMyOrders(@QueryMap Map<String, String> params);

    @GET("api/getMyPaymentCards")
    Observable<Object> getMyPaymentCards(@QueryMap Map<String, String> params);

    @GET("api/getTopRatedServiceProviders")
    Observable<Object> getTopRated(@QueryMap Map<String, String> params);

    @GET("api/getServiceProviders")
    Observable<Object> search(@QueryMap Map<String, String> params);

    @GET("api/getFilteredProvidersOrServices")
    Observable<Object> searchFirst(@QueryMap Map<String, String> params);

    @GET("api/place/nearbysearch/json")
    Observable<Object> nearBySearch(@QueryMap Map<String, String> params);

    @Multipart
    @POST("api/deleteAddress")
    Observable<Object> deleteAddress(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/deleteNotification")
    Observable<Object> deleteNotification(@PartMap Map<String, RequestBody> params);

    @GET("brand-vehicle")
    Observable<Object> getBrandVehicle(@Header("Authorization") String Token , @Query("brand_id") int brandId);

    @GET("dashboard")
    Observable<Object> getDashboardDataWithoutToke();

    @GET("dashboard2")
    Observable<Object> getDashboardData(@Header("Authorization") String Token);

    @GET("vehicles-by-ID-new")
    Observable<Object> getVehicles(@Header("Authorization") String Token,@QueryMap Map<String, String> params);


    @GET("banner")
    Observable<Object> getBanners();

    @GET("filter-vehicles-new")
    Observable<Object> filterVehicles(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    @POST("initiate-chat")
    Observable<Object> initiateChat(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

    // Send chat message endpoint (use query params)
    @POST("chat")
    Observable<Object> sendMessage(@Header("Authorization") String Token, @QueryMap Map<String, String> params);

//    @GET("get-chat-detail")
//    Observable<ConvoResponse> getChatDetail(@Header("Authorization") String Token, @QueryMap Map<String, String> params);
}
