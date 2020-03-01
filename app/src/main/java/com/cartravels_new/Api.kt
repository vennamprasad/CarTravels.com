package com.cartravels_new

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

	@FormUrlEncoded
	@POST("authentication/signup")
	fun createUser(
			@Field("name") name: String,
			@Field("email") email: String,
			@Field("mobile") mobile: String,
			@Field("password") password: String,
			@Field("confirmPassword") confirmPassword: String
	): Call<ResponseBody>

	@FormUrlEncoded
	@POST("authentication/signin")
	fun loginUser(@Field("email") email: String,
	              @Field("password") password: String): Call<ResponseBody>


	@FormUrlEncoded
	@POST("authentication/forgotpassword")
	fun forgotPassword(@Field("email") email: String): Call<ResponseBody>

	@FormUrlEncoded
	@POST("authentication/resetCode")
	fun resetPassword(@Field("uniid") uniid: String, @Field("passcode") passcode: String): Call<ResponseBody>

	@FormUrlEncoded
	@POST("authentication/updateChangePassword")
	fun changePassword(@Field("uniid") uniid: String, @Field("code") passcode: String, @Field("password") password: String, @Field("confirmPassword") confirmPassword: String): Call<ResponseBody>
}