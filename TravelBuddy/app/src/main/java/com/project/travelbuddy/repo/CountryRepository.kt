package com.project.travelbuddy.repo

import com.project.travelbuddy.api.ApiService
import com.project.travelbuddy.db.CountryDao
import com.project.travelbuddy.db.CountryEntity

class CountryRepository(private val countryDao: CountryDao) {

    suspend fun getCountryData(countryName: String): CountryEntity {
        // Check if country data is available offline
        var country = countryDao.getCountryByName(countryName)

        if (country == null) {
            // Fetch from API if not available offline
            val countryFromApi = ApiService.retrofitService.getCountryByName(countryName).first()
            country = CountryEntity(
                countryFromApi.name.common,
                countryFromApi.capital.firstOrNull() ?: "",
                countryFromApi.region,
                countryFromApi.population,
                countryFromApi.flags.png
            )
            // Save to Room
            countryDao.insert(country)
        }
        return country
    }
}
