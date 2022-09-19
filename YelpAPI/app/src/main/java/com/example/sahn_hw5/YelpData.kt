package com.example.sahn_hw5

data class YelpData(
    val total: Int,
    val businesses: List<Restaurants>
)

data class Restaurants(
    val rating: Float,
    val price: String,
    val categories: List<Categories>,
    val review_count: Int,
    val name: String,
    val image_url: String,
    val location: Location,
    val distance: Float,

)
data class Location(
    val address1: String
)

data class Categories(
    val title: String
)


