package com.banap.banap.app.navigation.screens

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen (
    val route: String
) {
    data object NewField : Screen(route = "NewField") {
        const val PRODUCER_ARGUMENT = "producerId"
        const val PROPERTY_ARGUMENT = "propertyId"
        const val FIELD_ARGUMENT = "fieldId"
        val routeWithArgument = "$route/{$PRODUCER_ARGUMENT}/{$PROPERTY_ARGUMENT}/{$FIELD_ARGUMENT}"

        val arguments = listOf(
            navArgument(PRODUCER_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PROPERTY_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(FIELD_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(producerId: String, propertyId: String, fieldId: String) = "$route/$producerId/$propertyId/$fieldId"
    }

    data object Information : Screen(route = "Information") {
        const val FIELD_ARGUMENT = "fieldId"
        const val USER_ARGUMENT = "userName"
        val routeWithArgument = "$route/{$FIELD_ARGUMENT}/{$USER_ARGUMENT}"

        val arguments = listOf(
            navArgument(FIELD_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(USER_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(fieldId: String, userName: String? = null) = "$route/$fieldId/$userName"
    }

    data object Property : Screen(route = "Property") {
        const val PROPERTY_NAME_ARGUMENT = "name"
        const val USERNAME_ARGUMENT = "userName"
        const val PROPERTY_ID_ARGUMENT = "propertyId"
        const val PRODUCER_ARGUMENT = "producerId"
        val routeWithArgument = "$route/{$PROPERTY_NAME_ARGUMENT}/{$USERNAME_ARGUMENT}/{$PROPERTY_ID_ARGUMENT}/{$PRODUCER_ARGUMENT}"

        val arguments = listOf(
            navArgument(PROPERTY_NAME_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(USERNAME_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PROPERTY_ID_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(PRODUCER_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(name: String, userName: String, propertyId: String, producerId: String) = "$route/$name/$userName/$propertyId/$producerId"
    }

    data object AnalysisInformation : Screen(route = "AnalysisInformation") {
        const val ANALYSIS_ARGUMENT = "analysisId"
        const val ANALYSIS_NAME_ARGUMENT = "analysisName"
        const val FIELD_NAME_ARGUMENT = "fieldName"
        val routeWithArgument = "$route/{$ANALYSIS_ARGUMENT}/{$ANALYSIS_NAME_ARGUMENT}/{$FIELD_NAME_ARGUMENT}"

        val arguments = listOf(
            navArgument(ANALYSIS_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(ANALYSIS_NAME_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(FIELD_NAME_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(analysisId: String, analysisName: String, fieldName: String) = "$route/$analysisId/$analysisName/$fieldName"
    }

    data object ExplanationFormData : Screen(route = "ExplanationFormData") {
        const val EXPLANATION_ARGUMENT = "id"
        const val EXPLANATION_TYPE_USER = "screen"
        val routeWithArgument = "$route/{$EXPLANATION_ARGUMENT}/{$EXPLANATION_TYPE_USER}"

        val arguments = listOf(
            navArgument(EXPLANATION_ARGUMENT) {
                type = NavType.StringType
            },
            navArgument(EXPLANATION_TYPE_USER) {
                type = NavType.StringType
            }
        )

        fun createRoute(id: String, typeUser: String) = "$route/$id/$typeUser"
    }

    data object NewProperty : Screen(route = "NewProperty") {
        const val PROPERTY_ARGUMENT = "propertyId"
        val routeWithArgument = "$route/{$PROPERTY_ARGUMENT}"

        val arguments = listOf(
            navArgument(PROPERTY_ARGUMENT) {
                type = NavType.StringType
            }
        )

        fun createRoute(propertyId: String) = "$route/$propertyId"
    }

//    data object UpdateUserInformation : Screen(route = "UpdateUserInformation") {
//        const val USER_NAME_ARGUMENT = "name"
//        const val USER_EMAIL_ARGUMENT = "email"
//        val routeWithArgument = "$route/{$USER_NAME_ARGUMENT}/{$USER_EMAIL_ARGUMENT}"
//
//        val arguments = listOf(
//            navArgument(USER_NAME_ARGUMENT) {
//                type = NavType.StringType
//            },
//            navArgument(USER_EMAIL_ARGUMENT) {
//                type = NavType.StringType
//            }
//        )
//
//        fun createRoute(name: String, email: String) = "$route/$name/$email"
//    }
}