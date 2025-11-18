package com.banap.banap.app.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.navigation.viewmodel.NavigationViewModel
import com.banap.banap.app.presentation.analysis.ui.engineer.information.screen.ClientAnalysis
import com.banap.banap.app.presentation.analysis.ui.engineer.registration.NewEngineerFertilizationRecommendationAnalysis
import com.banap.banap.app.presentation.analysis.ui.engineer.registration.NewEngineerLimingAnalysis
import com.banap.banap.app.presentation.analysis.ui.listing.screen.Analysis
import com.banap.banap.app.presentation.analysis.ui.producer.information.screen.AnalysisInformation
import com.banap.banap.app.presentation.analysis.ui.readmore.screen.ReadMore
import com.banap.banap.app.presentation.analysis.ui.producer.registration.screen.ExplanationFormData
import com.banap.banap.app.presentation.analysis.ui.producer.registration.screen.NewFertilizationRecommendation
import com.banap.banap.app.presentation.analysis.ui.producer.registration.screen.NewLimingCalculation
import com.banap.banap.app.presentation.client.ui.information.screen.ClientInformation
import com.banap.banap.app.presentation.client.ui.listing.screen.Clients
import com.banap.banap.app.presentation.client.ui.registration.screen.ClientAggregation
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerFirstPage
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerSecondPage
import com.banap.banap.app.presentation.field.ui.engineer.information.screen.ClientField
import com.banap.banap.app.presentation.field.ui.engineer.observation.screen.NewObservation
import com.banap.banap.app.presentation.field.ui.producer.information.screen.FieldInformation
import com.banap.banap.app.presentation.field.ui.producer.registration.screen.NewField
import com.banap.banap.app.presentation.home.ui.components.Settings
import com.banap.banap.app.presentation.home.ui.components.UpdatePassword
import com.banap.banap.app.presentation.home.ui.engineer.screen.EngineerHome
import com.banap.banap.app.presentation.home.ui.producer.screen.Home
import com.banap.banap.app.presentation.home.ui.components.UpdateUserNameAndEmail
import com.banap.banap.app.presentation.login.ui.screen.Login
import com.banap.banap.app.presentation.measurementsandconversions.ui.registration.screen.NewMeasurementAndConversionsCalculation
import com.banap.banap.app.presentation.producer.ui.registration.NewProducer
import com.banap.banap.app.presentation.property.ui.engineer.listing.ClientProperty
import com.banap.banap.app.presentation.property.ui.producer.listing.screen.Property
import com.banap.banap.app.presentation.property.ui.producer.registration.NewProperty
import com.banap.banap.app.presentation.readytostart.ui.ReadyToStart
import com.banap.banap.app.presentation.scanner.ui.screen.Scanner
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.singlespacing.ui.registration.screen.NewSingleSpacingCalculation
import com.banap.banap.app.presentation.task.ui.registration.screen.NewTask
import com.banap.banap.app.presentation.tools.ui.listing.Tools
import com.banap.banap.app.presentation.tutorial.ui.Tutorial
import com.banap.banap.app.presentation.userchoice.ui.UserChoice
import com.banap.banap.core.ui.components.SplashScreen
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.TaskList
import com.banap.banap.domain.viewmodel.analysis.ListAnalysisViewModel
import com.banap.banap.domain.viewmodel.engineer.GetEngineerByIdViewModel
import com.banap.banap.domain.viewmodel.field.ListFieldsViewModel
import com.banap.banap.domain.viewmodel.location.LocationViewModel
import com.banap.banap.domain.viewmodel.producer.GetProducerByIdViewModel
import com.banap.banap.domain.viewmodel.property.ListPropertiesViewModel
import com.banap.banap.domain.viewmodel.token.TokenVerificationViewModel
import com.banap.banap.domain.viewmodel.weather.WeatherViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    val tokenViewModel: TokenViewModel = hiltViewModel()
    val tokenVerificationViewModel: TokenVerificationViewModel = hiltViewModel()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val locationViewModel: LocationViewModel = hiltViewModel()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val listPropertiesViewModel: ListPropertiesViewModel = hiltViewModel()
    val listFieldsViewModel: ListFieldsViewModel = hiltViewModel()
    val listAnalysisViewModel: ListAnalysisViewModel = hiltViewModel()
    val getProducerByIdViewModel: GetProducerByIdViewModel = hiltViewModel()
    val getEngineerByIdViewModel: GetEngineerByIdViewModel = hiltViewModel()
    val animationDuration: Int = 700

    val taskListHome: MutableList<TaskList> = mutableListOf()
    val taskListFieldInformation: MutableList<String> = mutableListOf()
    val logList: MutableList<LogList> = mutableListOf()

    LaunchedEffect(navigationController) {
        navigationController
            .currentBackStackEntryFlow
            .mapNotNull { it.destination.route }
            .filter { it != "SplashScreen" }
            .distinctUntilChanged()
            .collect {
                Log.d("Navigation route", it)
                navigationViewModel.recordRoute(it)
            }
    }

    NavHost(
        navController = navigationController,
        startDestination = "SplashScreen"
    ) {
        composable(
            route = "SplashScreen",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            SplashScreen(
                navigationController,
                tokenViewModel = tokenViewModel,
                tokenVerificationViewModel = tokenVerificationViewModel,
                navigationViewModel = navigationViewModel
            )
        }

        composable(
            route = "Home",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Home(
                navigationController,
                tokenViewModel = tokenViewModel,
                tokenVerificationViewModel = tokenVerificationViewModel,
                weatherViewModel = weatherViewModel,
                locationViewModel = locationViewModel,
                listPropertiesViewModel = listPropertiesViewModel,
                listFieldsViewModel = listFieldsViewModel,
                getProducerByIdViewModel = getProducerByIdViewModel,
                taskList = taskListHome,
                logList = logList
            )
        }

        composable(
            route = "Login",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Login(
                navigationController,
                tokenViewModel = tokenViewModel,
                tokenVerificationViewModel = tokenVerificationViewModel
            )
        }

        composable(
            route = Screen.NewProperty.routeWithArgument,
            arguments = Screen.NewProperty.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val propertyId =
                backStackEntry.arguments?.getString(Screen.NewProperty.PROPERTY_ARGUMENT)

            NewProperty(
                navigationController,
                logList = logList,
                tokenViewModel = tokenViewModel,
                propertyId = propertyId ?: ""
            )
        }

        composable(
            route = "NewProducer",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewProducer(
                navigationController = navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = "NewEngineerFirstPage",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewEngineerFirstPage(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = "NewEngineerSecondPage",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewEngineerSecondPage(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = "Tutorial",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Tutorial(navigationController)
        }

        composable(
            route = "ReadyToStart",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ReadyToStart(navigationController)
        }

        composable(
            route = "UserChoice",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            UserChoice(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = Screen.NewField.routeWithArgument,
            arguments = Screen.NewField.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val producerId = backStackEntry.arguments?.getString(Screen.NewField.PRODUCER_ARGUMENT)
            val propertyId = backStackEntry.arguments?.getString(Screen.NewField.PROPERTY_ARGUMENT)
            val fieldId = backStackEntry.arguments?.getString(Screen.NewField.FIELD_ARGUMENT)

            NewField(
                navigationController = navigationController,
                producerId = producerId ?: "",
                propertyId = propertyId ?: "",
                fieldId = fieldId,
                tokenViewModel = tokenViewModel,
                logList = logList
            )
        }

        composable(
            route = Screen.Information.routeWithArgument,
            arguments = Screen.Information.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val fieldId = backStackEntry.arguments?.getString(Screen.Information.FIELD_ARGUMENT)
            val userName = backStackEntry.arguments?.getString(Screen.Information.USER_ARGUMENT)

            FieldInformation(
                navigationController = navigationController,
                tokenViewModel = tokenViewModel,
                fieldId = fieldId ?: "",
                userName = userName ?: "",
                listAnalysisViewModel = listAnalysisViewModel,
                taskList = taskListFieldInformation
            )
        }

        composable(
            route = "NewLimingCalculation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewLimingCalculation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = "NewFertilizationRecommendation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewFertilizationRecommendation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = Screen.ExplanationFormData.routeWithArgument,
            arguments = Screen.ExplanationFormData.arguments,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight ->
                        fullHeight
                    },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight ->
                        fullHeight
                    },
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val id =
                backStackEntry.arguments?.getString(Screen.ExplanationFormData.EXPLANATION_ARGUMENT)
            val typeUser = backStackEntry.arguments?.getString(Screen.ExplanationFormData.EXPLANATION_TYPE_USER)

            ExplanationFormData(
                navigationController,
                tokenViewModel = tokenViewModel,
                id = id ?: "",
                typeUser = typeUser ?: ""
            )
        }

        composable(
            route = Screen.Property.routeWithArgument,
            arguments = Screen.Property.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val propertyId =
                backStackEntry.arguments?.getString(Screen.Property.PROPERTY_ID_ARGUMENT)
            val producerId = backStackEntry.arguments?.getString(Screen.Property.PRODUCER_ARGUMENT)
            val name = backStackEntry.arguments?.getString(Screen.Property.PROPERTY_NAME_ARGUMENT)
            val userName = backStackEntry.arguments?.getString(Screen.Property.USERNAME_ARGUMENT)

            Property(
                navigationController = navigationController,
                name = name ?: "",
                userName = userName ?: "",
                propertyId = propertyId ?: "",
                producerId = producerId ?: "",
                listFieldsViewModel = listFieldsViewModel,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = "NewTask",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewTask(
                navigationController,
                taskListHome = taskListHome,
                taskListFieldInformation = taskListFieldInformation,
                logList = logList,
                tokenViewModel = tokenViewModel
            )
        }

        composable(
            route = Screen.AnalysisInformation.routeWithArgument,
            arguments = Screen.AnalysisInformation.arguments,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) { backStackEntry ->
            val analysisId =
                backStackEntry.arguments?.getString(Screen.AnalysisInformation.ANALYSIS_ARGUMENT)
            val analysisName =
                backStackEntry.arguments?.getString(Screen.AnalysisInformation.ANALYSIS_NAME_ARGUMENT)
            val fieldName =
                backStackEntry.arguments?.getString(Screen.AnalysisInformation.FIELD_NAME_ARGUMENT)

            AnalysisInformation(
                navigationController,
                tokenViewModel = tokenViewModel,
                analysisId = analysisId ?: "",
                analysisName = analysisName ?: "",
                fieldName = fieldName ?: ""
            )
        }

        composable(
            route = "ReadMore",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ReadMore(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "UpdateUserNameAndEmail",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            UpdateUserNameAndEmail(
                navigationController,
                tokenViewModel = tokenViewModel,
                getProducerByIdViewModel = getProducerByIdViewModel,
                getEngineerByIdViewModel = getEngineerByIdViewModel
            )
        }

        composable (
            route = "EngineerHome",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            EngineerHome(
                navigationController,
                tokenViewModel = tokenViewModel,
                tokenVerificationViewModel = tokenVerificationViewModel,
                locationViewModel = locationViewModel,
                weatherViewModel = weatherViewModel,
                getEngineerByIdViewModel = getEngineerByIdViewModel,
                logList = logList
            )
        }

        composable (
            route = "Clients",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Clients(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "ClientInformation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ClientInformation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "ClientProperty",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ClientProperty(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "ClientField",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ClientField(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "ClientAnalysis",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ClientAnalysis(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "ClientAggregation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            ClientAggregation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "NewObservation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewObservation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "Analysis",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Analysis(
                navigationController,
                tokenViewModel = tokenViewModel,
                listAnalysisViewModel = listAnalysisViewModel
            )
        }

        composable (
            route = "Tools",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Tools(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "NewEngineerLimingAnalysis",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewEngineerLimingAnalysis(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "NewEngineerFertilizationRecommendationAnalysis",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewEngineerFertilizationRecommendationAnalysis(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "NewSingleSpacingCalculation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewSingleSpacingCalculation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "NewMeasurementAndConversionsCalculation",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            NewMeasurementAndConversionsCalculation(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "Scanner",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Scanner(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "Settings",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            Settings(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }

        composable (
            route = "UpdatePassword",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth ->
                        fullWidth
                    },
                    animationSpec = tween(animationDuration)
                )
            }
        ) {
            UpdatePassword(
                navigationController,
                tokenViewModel = tokenViewModel
            )
        }
    }
}