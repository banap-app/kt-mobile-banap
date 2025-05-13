package com.banap.banap.core.ui.util

import com.banap.banap.domain.model.analysis.NPKResult

object FertilizerCalculator {

    fun calculateNPK(phosphor: Double, potassium: Double, expectedProductivity: Int) : NPKResult {
        var nitrogen: Int = 0
        var phosphorValue: Int = 0
        var potassiumValue: Int = 0

        when {
            expectedProductivity > 50 -> {
                nitrogen = 410
                potassiumValue = when {
                    potassium < 1.6 -> 800
                    potassium <= 3.0 -> 950
                    else -> 750
                }
                phosphorValue = when {
                    phosphor < 16 -> 240
                    phosphor <= 40 -> 150
                    else -> 120
                }
            }

            expectedProductivity in 40..50 -> {
                nitrogen = 340
                potassiumValue = when {
                    potassium < 1.6 -> 800
                    potassium <= 3.0 -> 750
                    else -> 550
                }
                phosphorValue = when {
                    phosphor < 16 -> 220
                    phosphor <= 40 -> 130
                    else -> 110
                }
            }

            expectedProductivity in 30..39 -> {
                nitrogen = 260
                potassiumValue = when {
                    potassium < 1.6 -> 800
                    potassium <= 3.0 -> 550
                    else -> 350
                }
                phosphorValue = when {
                    phosphor < 16 -> 200
                    phosphor <= 40 -> 110
                    else -> 80
                }
            }

            expectedProductivity in 20..29 -> {
                nitrogen = 190
                potassiumValue = when {
                    potassium < 1.6 -> 600
                    potassium <= 3.0 -> 350
                    else -> 150
                }
                phosphorValue = when {
                    phosphor < 16 -> 180
                    phosphor <= 40 -> 90
                    else -> 60
                }
            }

            else -> {
                nitrogen = 110
                potassiumValue = when {
                    potassium < 1.6 -> 400
                    potassium <= 3.0 -> 150
                    else -> 100
                }
                phosphorValue = when {
                    phosphor < 16 -> 160
                    phosphor <= 40 -> 70
                    else -> 40
                }
            }

        }

        return NPKResult(
            nitrogen = nitrogen,
            phosphor = phosphorValue,
            potassium = potassiumValue
        )
    }
}