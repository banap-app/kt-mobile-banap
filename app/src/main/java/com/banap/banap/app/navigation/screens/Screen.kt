package com.banap.banap.app.navigation.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen (
    val route: String
) {
    data object NewField : Screen(route = "NewField") {
        const val PRODUCER_ARGUMENT = "producerId"
        const val PROPERTY_ARGUMENT = "propertyId"
        val routeWithArgument = "$route/{$PRODUCER_ARGUMENT}/{$PROPERTY_ARGUMENT}"

        val arguments = listOf(
            navArgument(PRODUCER_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PROPERTY_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(producerId: String, propertyId: String) = "$route/$producerId/$propertyId"
    }

    data object Information : Screen(route = "Information") {
        const val FIELD_ARGUMENT = "fieldId"
        val routeWithArgument = "$route/{$FIELD_ARGUMENT}"

        val arguments = listOf(
            navArgument(FIELD_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(fieldId: String) = "$route/$fieldId"
    }

    data object Property : Screen(route = "Property") {
        const val PROPERTY_NAME_ARGUMENT = "name"
        const val PROPERTY_ID_ARGUMENT = "propertyId"
        const val PRODUCER_ARGUMENT = "producerId"
        val routeWithArgument = "$route/{$PROPERTY_NAME_ARGUMENT}/{$PROPERTY_ID_ARGUMENT}/{$PRODUCER_ARGUMENT}"

        val arguments = listOf(
            navArgument(PROPERTY_NAME_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PROPERTY_ID_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PRODUCER_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(name: String, propertyId: String, producerId: String) = "$route/$name/$propertyId/$producerId"
    }
}