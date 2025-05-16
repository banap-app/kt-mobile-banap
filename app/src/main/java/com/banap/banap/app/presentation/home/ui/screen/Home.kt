package com.banap.banap.app.presentation.home.ui.screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.home.ui.components.Carousel
import com.banap.banap.app.presentation.home.ui.components.Header
import com.banap.banap.app.presentation.home.ui.components.Property
import com.banap.banap.app.presentation.home.ui.components.RecentActivities
import com.banap.banap.app.presentation.home.ui.components.Tasks
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.home.HomeSkeleton
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.Task
import com.banap.banap.data.model.producer.TaskList
import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.domain.viewmodel.location.LocationViewModel
import com.banap.banap.domain.viewmodel.token.TokenVerificationViewModel
import com.banap.banap.domain.viewmodel.weather.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    tokenVerificationViewModel: TokenVerificationViewModel,
    weatherViewModel: WeatherViewModel,
    locationViewModel: LocationViewModel
) {
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val tokenVerificationState = tokenVerificationViewModel.state.value
    val weatherState = weatherViewModel.state.value
    val locationState = locationViewModel.state.value

    var isTokenValid: Boolean by remember {
        mutableStateOf(false)
    }

    var hasToken: String? by remember {
        mutableStateOf(null)
    }

    var weather: WeatherResponse? by remember {
        mutableStateOf(null)
    }

    var weatherLoading: Boolean by remember {
        mutableStateOf(true)
    }

    val taskList: MutableList<TaskList> = mutableListOf(
        TaskList(
            propertyName = "Propriedade 01",
            tasks = listOf(
                Task(
                    fieldName = "Talhão 01",
                    taskName = listOf(
                        "Semear",
                        "Cultivar"
                    )
                )
            )
        ),
        TaskList(
            propertyName = "Propriedade 02",
            tasks = listOf(
                Task(
                    fieldName = "Talhão 01",
                    taskName = listOf(
                        "Semear"
                    )
                )
            )
        )
    )

    val logList: MutableList<LogList> = mutableListOf(
        LogList(
            author = "Gilmar",
            activity = "cadastrou uma propriedade."
        ),
        LogList(
            author = "Gilmar",
            activity = "criou um talhão."
        )
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            locationViewModel.getCurrentLocation()
        } else {
            scope.launch {
                val autoDismissJob = launch {
                    delay(5_000L)
                    snackBarHostState.currentSnackbarData?.dismiss()
                }

                snackBarHostState.showSnackbar(
                    message = "Usaremos um valor padrão para mostrar o clima!",
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )

                autoDismissJob.cancel()
            }
        }
    }

    LaunchedEffect(true) {
        delay(1_000)
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(locationState.response) {
        locationState.response?.let {
            tokenViewModel.saveTokens(
                mapOf(
                    "latitude" to it.latitude.toString(),
                    "longitude" to it.longitude.toString()
                )
            )

            weatherViewModel.getCurrentWeather(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }

    LaunchedEffect(true) {
        if (!tokenViewModel.getToken("latitude").isNullOrEmpty() && !tokenViewModel.getToken("longitude").isNullOrEmpty()) {
            var latitude = -24.714174
            var longitude = -47.8870154

            Log.d("LATITUDE", tokenViewModel.getToken("latitude") ?: "")
            Log.d("LONGITUDE", tokenViewModel.getToken("longitude") ?: "")

            tokenViewModel.getToken("latitude")?.let {
                Log.d("LATITUDE", "latitude não nula - $it")
                latitude = it.toDouble()
            }

            tokenViewModel.getToken("longitude")?.let {
                Log.d("LONGITUDE", "longitude não nula - $it")
                longitude = it.toDouble()
            }

            weatherViewModel.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
        } else {
            weatherViewModel.getCurrentWeather()
        }
    }

    LaunchedEffect(weatherState.isLoading) {
        weatherLoading = weatherState.isLoading
    }

    LaunchedEffect(weatherState.response) {
        weatherState.response?.let {
            Log.d("WEATHER", it.toString())
            weather = weatherState.response
        }
    }

    LaunchedEffect(context) {
        Log.d("TOKEN", tokenViewModel.getToken("token").toString())

        tokenViewModel.getToken("token")?.let { token ->
            hasToken = token
            tokenVerificationViewModel.verifyToken(token)
        } ?: run {
            Log.d(
                "NO_TOKEN",
                "Voce nao possui um token de autenticação e será redirecionado para tela de login"
            )
            delay(1_000)
            tokenViewModel.clearAll()
            navigationController.navigate("Login")
        }
    }

    LaunchedEffect(tokenVerificationState.response) {
        Log.d("RESPONSE", tokenVerificationState.response?.decodedToken ?: "sem resposta")

        tokenVerificationState.response?.let {
            tokenViewModel.saveToken("verifiedToken", it.success.toString())
            isTokenValid = it.success
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        Log.d("ERROR", tokenVerificationState.error)

        if (tokenVerificationState.error.isNotEmpty()) {
            tokenViewModel.clearAll()
            navigationController.navigate("Login")
        }
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
        if (isTokenValid && !hasToken.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 60.dp),
            ) {
                Header(
                    nome = "Gilmar",
                    navigationController = navigationController,
                    onItemClick = {
                        tokenViewModel.clearAll()
                        navigationController.navigate("Login")
                    }
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas),
                    contentDescription = "Linhas que separam o conteúdo",
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            bottom = 40.dp
                        )
                )

                Carousel(
                    isLoading = weatherLoading,
                    weather = weather,
                    temperature = weather?.main?.temp ?: 0.0,
                    description = weather?.weather?.get(0)?.description ?: "",
                    iconUrl = "https://openweathermap.org/img/wn/${weather?.weather?.get(0)?.icon}@2x.png"
                )

                Spacer(modifier = Modifier.height(60.dp))

                Property(
                    titulo = "Propriedade 01",
                    navigationController = navigationController
                )

                Spacer(modifier = Modifier.height(60.dp))

                RecentActivities(
                    title = "Atividades recentes",
                    list = logList
                )

                Spacer(modifier = Modifier.height(60.dp))

                Tasks(
                    titulo = "Lista de tarefas",
                    subTitulo = "Seus afazeres da semana!",
                    navigationController = navigationController,
                    list = taskList
                )
            }
        } else {
            HomeSkeleton(
                padding = innerPadding.calculateTopPadding()
            )
        }
    }
}