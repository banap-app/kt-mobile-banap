package com.banap.banap.app.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.navigation.viewmodel.NavigationViewModel
import com.banap.banap.app.presentation.analysis.ui.information.screen.AnalysisInformation
import com.banap.banap.app.presentation.analysis.ui.readmore.screen.ReadMore
import com.banap.banap.app.presentation.analysis.ui.registration.screen.ExplanationFormData
import com.banap.banap.app.presentation.analysis.ui.registration.screen.NewFertilizationRecommendation
import com.banap.banap.app.presentation.analysis.ui.registration.screen.NewLimingCalculation
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerFirstPage
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerSecondPage
import com.banap.banap.app.presentation.field.ui.information.screen.FieldInformation
import com.banap.banap.app.presentation.field.ui.registration.screen.NewField
import com.banap.banap.app.presentation.home.ui.screen.Home
import com.banap.banap.app.presentation.login.ui.screen.Login
import com.banap.banap.app.presentation.producer.ui.registration.NewProducer
import com.banap.banap.app.presentation.property.ui.listing.screen.Property
import com.banap.banap.app.presentation.property.ui.registration.NewProperty
import com.banap.banap.app.presentation.readytostart.ui.ReadyToStart
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.task.ui.registration.screen.NewTask
import com.banap.banap.app.presentation.tutorial.ui.Tutorial
import com.banap.banap.app.presentation.userchoice.ui.UserChoice
import com.banap.banap.core.ui.components.SplashScreen
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.TaskList
import com.banap.banap.domain.viewmodel.field.ListFieldsViewModel
import com.banap.banap.domain.viewmodel.location.LocationViewModel
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
    val animationDuration: Int = 700

    val analysisList: MutableList<String> = mutableListOf()
    val taskListHome: MutableList<TaskList> = mutableListOf()
    val taskListFieldInformation: MutableList<String> = mutableListOf()
    val logList: MutableList<LogList> = mutableListOf()

    val listPropertiesState = listPropertiesViewModel.state.value
    val listFieldsState = listFieldsViewModel.state.value
    val weatherState = weatherViewModel.state.value

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
    ){
        composable (
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

        composable (
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
                weatherState = weatherState,
                locationViewModel = locationViewModel,
                listPropertiesViewModel = listPropertiesViewModel,
                listPropertiesState = listPropertiesState,
                listFieldsViewModel = listFieldsViewModel,
                listFieldsState = listFieldsState,
                taskList = taskListHome,
                logList = logList
            )
        }

        composable (
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

        composable (
            route = "NewProperty",
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
            NewProperty(
                navigationController,
                logList = logList
            )
        }

        composable (
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

        composable (
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
            NewEngineerFirstPage(navigationController)
        }

        composable (
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
            NewEngineerSecondPage(navigationController)
        }

        composable (
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

        composable (
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

        composable (
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
            UserChoice(navigationController)
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

            NewField(
                navigationController = navigationController,
                producerId = producerId ?: "",
                propertyId = propertyId ?: "",
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

            FieldInformation(
                navigationController = navigationController,
                tokenViewModel = tokenViewModel,
                fieldId = fieldId ?: "",
                analysisList = analysisList,
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
            NewLimingCalculation(navigationController)
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
                analysisList = analysisList,
                logList = logList
            )
        }

        composable(
            route = "ExplanationFormData",
            enterTransition = {
                slideInVertically (
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
        ) {
            ExplanationFormData(navigationController)
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
            val propertyId = backStackEntry.arguments?.getString(Screen.Property.PROPERTY_ID_ARGUMENT)
            val producerId = backStackEntry.arguments?.getString(Screen.Property.PRODUCER_ARGUMENT)
            val name = backStackEntry.arguments?.getString(Screen.Property.PROPERTY_NAME_ARGUMENT)

            Property(
                navigationController = navigationController,
                name = name ?: "",
                propertyId = propertyId ?: "",
                producerId = producerId ?: "",
                listFieldsViewModel = listFieldsViewModel,
                listFieldsState = listFieldsState,
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
                logList = logList
            )
        }

        composable(
            route = "AnalysisInformation",
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
            AnalysisInformation(navigationController)
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
            ReadMore(navigationController)
        }
    }
}