package com.banap.banap.app.presentation.field.ui.registration.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.field.ui.registration.components.FirstPage
import com.banap.banap.app.presentation.field.ui.registration.components.SecondPage
import com.banap.banap.app.presentation.field.ui.registration.components.ThirdPage
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.field.viewmodel.FieldNameTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.util.enumerator.FieldPage
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.data.model.producer.LogList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
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
    val scope = rememberCoroutineScope()

    var currentPage = remember {
        mutableStateOf(
            when (tokenViewModel.getToken("currentPage")) {
                "FIRST" -> FieldPage.FIRST
                "SECOND" -> FieldPage.SECOND
                "THIRD" -> FieldPage.THIRD
                else -> FieldPage.FIRST
            }
        )
    }

    val viewModelName = viewModel<FieldNameTextFieldViewModel>()
    val stateName = viewModelName.state

    var markers = tokenViewModel.markers

    var firstStep = remember {
        mutableStateOf(false)
    }

    var secondStep = remember {
        mutableStateOf(false)
    }

    var thirdStep = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("fieldName")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        if (!producerId.contains("producerId")) {
            tokenViewModel.saveToken("producerId", producerId)
        }
    }

    LaunchedEffect(true) {
        if (!propertyId.contains("propertyId")) {
            tokenViewModel.saveToken("propertyId", propertyId)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.loadMarkers("markers")
    }

    LaunchedEffect(Unit) {
        snapshotFlow { markers.toList() }
            .distinctUntilChanged()
            .debounce(300)
            .collectLatest { list ->
                Log.d("MARKERS", list.toString())

                if (list.isNotEmpty()) {
                    tokenViewModel.saveMarkers("markers", list)
                }
            }
    }

    LaunchedEffect(currentPage.value) {
        tokenViewModel.saveToken("currentPage", currentPage.value.name)
        Log.d("CURRENT_PAGE", currentPage.value.name)
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
    ) { innerPadding ->
        when (currentPage.value) {
            FieldPage.FIRST -> {
                FirstPage(
                    navigationController = navigationController,
                    currentPage = currentPage,
                    context = context,
                    viewModel = viewModelName,
                    state = stateName,
                    isValidationSuccessful = firstStep,
                    innerPadding = innerPadding,
                    tokenViewModel = tokenViewModel
                )
            }

            FieldPage.SECOND -> {
                SecondPage(
                    currentPage = currentPage,
                    snackBarHostState = snackBarHostState,
                    context = context,
                    scope = scope,
                    markers = markers,
                    tokenViewModel = tokenViewModel,
                    isValidationSuccessful = secondStep,
                    innerPadding = innerPadding
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
                    logList = logList,
                    isValidationSuccessful = thirdStep,
                    innerPadding = innerPadding,
                    tokenViewModel = tokenViewModel
                )
            }
        }
    }
}