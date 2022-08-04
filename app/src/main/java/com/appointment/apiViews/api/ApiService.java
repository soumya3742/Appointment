package com.appointment.apiViews.api;

import com.appointment.apiViews.model.CityModel;
import com.appointment.apiViews.model.DoctorDetails;
import com.appointment.apiViews.model.DoctorListModel;
import com.appointment.apiViews.model.DoctorModel;
import com.appointment.apiViews.model.GeneralModel;
import com.appointment.apiViews.model.LoginModel;
import com.appointment.apiViews.model.OrderList;
import com.appointment.apiViews.model.ProfileModel;
import com.appointment.apiViews.model.SignUpModel;
import com.appointment.apiViews.model.StateModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    /*Login Api */
    @FormUrlEncoded
    @POST("login")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<LoginModel> login(@Field("input") String mobile,
                           @Field("password") String password);

    /*forgot password Api */
    @FormUrlEncoded
    @POST("forgot-password")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<GeneralModel> forgotPass(@Field("mobile") String mobile);


    /* Register api */
    @FormUrlEncoded
    @POST("register")
    Call<SignUpModel> getSignUp(@Field("name") String name,
                                @Field("email") String email,
                                @Field("mobile") String mobile,
                                @Field("password") String password,
                                @Field("referralCode") String referralCode,
                                @Field("state_id") String state_id,
                                @Field("city_id") String city_id);


    /*Profile Api */
    @FormUrlEncoded
    @POST("myprofile")
    Call<ProfileModel> getProfile(@Field("user_id") int id);

    /*update profile Api */
    @FormUrlEncoded
    @POST("update-myprofile")
    Call<ProfileModel> getUpdate(@Field("user_id") int id,
                                 @Field("name") String name,
                                 @Field("email") String email,
                                 @Field("mobile") String mobile,
                                 @Field("business_name") String gender,
                                 @Field("state_name") String state,
                                 @Field("city_name") String city,
                                 @Field("pincode") String pincode
    );

    /*Medical store list  */
    @FormUrlEncoded
    @POST("my-order-medical")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<OrderList> getMyorder(@Field("user_id") int userId);

    /*Medical store list  */
    @FormUrlEncoded
    @POST("order-medical-cancel ")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<GeneralModel> orderCancel(@Field("order_id") String order_id);


    /*Doctor  list  */
    @FormUrlEncoded
    @POST("doctor-list")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<DoctorModel> getDoctorList(@Field("category_id") Integer id);

    /*Doctor search list  */
    @FormUrlEncoded
    @POST("doctor-list")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<DoctorModel> getDoctorSearchList(@Field("category_id") Integer id, @Field("search") String searchKey);


    /*Doctor  Details  */
    @FormUrlEncoded
    @POST("doctor-details")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<DoctorDetails> getDoctorDetails(@Field("doctor_id") Integer doctorid);

    /*Doctor  Appointmnet  */
    @FormUrlEncoded
    @POST("doctor-booking")
    // @POST("doctor-appointment")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<GeneralModel> getDoctoronAppointmnet(@Field("doctor_id") Integer doctorid,
                                              @Field("user_id") Integer user_id,
                                              @Field("timing") String timing,
                                              @Field("location_id") String typeId,
                                              @Field("name") String patientName,
                                              @Field("mobile") String patientPhone,
                                              @Field("email_id") String patientEmail,
                                              @Field("gender") String patientGender,
                                              @Field("dob") String patientDob,
                                              @Field("date") String currentDate,
                                              @Field("fees") String fees);


    /*get the list of state */
    @POST("states")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<StateModel> getState();

    /*get the list of state */
    @FormUrlEncoded
    @POST("cities")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<CityModel> getCity(@Field("state_id") String state_id);

    /*My Doctor  list  */
    @FormUrlEncoded
    @POST("user-doctor-list")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<DoctorListModel> getMyDoctorList(@Field("user_id") String userID);

}