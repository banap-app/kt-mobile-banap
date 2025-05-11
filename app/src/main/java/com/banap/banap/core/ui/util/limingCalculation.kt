package com.banap.banap.core.ui.util

fun limingCalculation(
    currentSba: Double,
    desiredSba: Double,
    ctc: Double,
    prnt: Double
) : Double {
    val deltaV = desiredSba - currentSba
    return (deltaV * ctc) / prnt
}