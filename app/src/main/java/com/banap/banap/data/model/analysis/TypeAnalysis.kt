package com.banap.banap.data.model.analysis

data class TypeAnalysis(
    val totalCationExchangeCapacity: Double? = null,
    val relativeTotalNeutralizingPower: Double? = null,
    val desiredBaseSaturation: Double? = null,
    val currentBaseSaturation: Double? = null,
    val phosphor: Double? = null,
    val potassium: Double? = null,
    val expectedProductivity: Int? = null
)