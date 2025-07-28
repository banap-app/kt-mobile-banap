package com.banap.banap.app.presentation.field.ui.registration.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.field.ui.registration.components.FirstPage
import com.banap.banap.app.presentation.field.ui.registration.components.SecondPage
import com.banap.banap.app.presentation.field.ui.registration.components.ThirdPage
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.field.viewmodel.FieldNameTextFieldViewModel
import com.banap.banap.app.util.enumerator.FieldPage
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.data.model.producer.LogList
import com.google.android.gms.maps.model.LatLng

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewField(
    navigationController: NavController,
    producerId: String,
    propertyId: String,
    tokenViewModel: TokenViewModel,
    logList: MutableList<LogList>
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    var currentPage = remember {
        mutableStateOf(
            FieldPage.FIRST
        )
    }

    val viewModelName = viewModel<FieldNameTextFieldViewModel>()
    val stateName = viewModelName.state

    var markers = remember {
        mutableStateListOf<LatLng>()
    }

    var firstStep = remember {
        mutableStateOf(false)
    }

    var secondStep = remember {
        mutableStateOf(false)
    }

    var thirdStep = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = VERDE_CLARO,
                    contentColor = BRANCO,
                    actionColor = BRANCO
                )
            }
        }
    ) {
        when (currentPage.value) {
            FieldPage.FIRST -> {
                FirstPage(
                    navigationController = navigationController,
                    currentPage = currentPage,
                    context = context,
                    viewModel = viewModelName,
                    state = stateName,
                    isValidationSuccessful = firstStep
                )
            }

            FieldPage.SECOND -> {
                SecondPage(
                    currentPage = currentPage,
                    snackBarHostState = snackBarHostState,
                    markers = markers,
                    tokenViewModel = tokenViewModel,
                    isValidationSuccessful = secondStep
                )
            }

            FieldPage.THIRD -> {
                ThirdPage(
                    navigationController = navigationController,
                    currentPage = currentPage,
                    snackBarHostState = snackBarHostState,
                    context = context,
                    producerId = producerId,
                    propertyId = propertyId,
                    stateName = stateName,
                    markers = markers,
                    logList = logList,
                    isValidationSuccessful = thirdStep
                )
            }
        }
    }
}