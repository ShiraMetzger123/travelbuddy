package com.project.travelbuddy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.travelbuddy.db.CountryEntity
import com.project.travelbuddy.repo.CountryRepository

class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _country = MutableLiveData<CountryEntity>()
    val country: LiveData<CountryEntity> get() = _country

    suspend fun fetchCountryData(countryName: String) {
        _country.value = repository.getCountryData(countryName)
    }
}