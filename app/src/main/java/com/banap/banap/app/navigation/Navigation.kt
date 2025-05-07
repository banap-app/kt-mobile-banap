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
import com.banap.banap.app.navigation.viewmodel.NavigationViewModel
import com.banap.banap.app.presentation.analysis.ui.registration.screen.ExplanationFormData
import com.banap.banap.app.presentation.analysis.ui.registration.screen.NewAnalysis
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerFirstPage
import com.banap.banap.app.presentation.engineer.ui.registration.NewEngineerSecondPage
import com.banap.banap.app.presentation.field.ui.information.screen.FieldInformation
import com.banap.banap.app.presentation.field.ui.registration.screen.NewFieldFirstPage
import com.banap.banap.app.presentation.field.ui.registration.screen.NewFieldSecondPage
import com.banap.banap.app.presentation.field.ui.registration.screen.NewFieldThirdPage
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
import com.banap.banap.domain.viewmodel.TokenVerificationViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    val tokenViewModel: TokenViewModel = hiltViewModel()
    val tokenVerificationViewModel: TokenVerificationViewModel = hiltViewModel()
    val navigationViewModel: NavigationViewModel = hiltViewModel()
    val animationDuration: Int = 700

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
                tokenVerificationViewModel = tokenVerificationViewModel
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
            NewProperty(navigationController)
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
            NewProducer(navigationController)
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

        composable (
            route = "NewFieldFirstPage",
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
            NewFieldFirstPage(navigationController)
        }

        composable (
            route = "NewFieldSecondPage",
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
            NewFieldSecondPage(navigationController)
        }

        composable (
            route = "NewFieldThirdPage",
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
            NewFieldThirdPage(navigationController)
        }

        composable(
            route = "Information",
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
            FieldInformation(navigationController)
        }

        composable(
            route = "NewAnalysis",
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
            NewAnalysis(navigationController)
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
            route = "Property",
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
            Property(navigationController)
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
            NewTask(navigationController)
        }
    }
}