package com.project.travelbuddy.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert
    suspend fun insert(country: CountryEntity)

    @Query("SELECT * FROM countries WHERE name = :countryName LIMIT 1")
    suspend fun getCountryByName(countryName: String): CountryEntity?
}