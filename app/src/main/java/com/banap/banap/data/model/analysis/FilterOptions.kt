package com.banap.banap.data.model.analysis

data class FilterOptions (
    val type: String? = null,
    val minPotassium: Double? = null,
    val maxPotassium: Double? = null,
    val minDate: String? = null,
    val maxDate: String? = null
)