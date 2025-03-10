package com.project.travelbuddy.api

import com.project.travelbuddy.model.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("v3.1/name/{countryName}")
    suspend fun getCountryByName(@Path("countryName") countryName: String): List<Country>
}