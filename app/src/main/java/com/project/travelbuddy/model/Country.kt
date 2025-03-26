package com.project.travelbuddy.model

data class Country(
    val name: Name,   // Change to Name object
    val capital: List<String>?,
    val region: String,
    val population: Long,
    val flags: Flags   // Change to Flags object
)

data class Name(
    val common: String,  // The common name of the country
    val official: String // The official name of the country
)

data class Flags(
    val png: String // URL of the flag image
)