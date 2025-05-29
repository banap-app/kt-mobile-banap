package com.banap.banap.app.presentation.home.ui.screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.home.ui.components.Carousel
import com.banap.banap.app.presentation.home.ui.components.Header
import com.banap.banap.app.presentation.home.ui.components.NoData
import com.banap.banap.app.presentation.home.ui.components.Property
import com.banap.banap.app.presentation.home.ui.components.RecentActivities
import com.banap.banap.app.presentation.home.ui.components.Tasks
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.home.components.ListPropertiesHomeSkeleton
import com.banap.banap.app.presentation.skeleton.ui.home.screen.HomeSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.TaskList
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.domain.model.property.ListPropertiesState
import com.banap.banap.domain.model.weather.WeatherState
import com.banap.banap.domain.viewmodel.field.ListFieldsViewModel
import com.banap.banap.domain.viewmodel.location.LocationViewModel
import com.banap.banap.domain.viewmodel.property.ListPropertiesViewModel
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
    weatherState: WeatherState,
    locationViewModel: LocationViewModel,
    listPropertiesViewModel: ListPropertiesViewModel,
    listPropertiesState: ListPropertiesState,
    listFieldsViewModel: ListFieldsViewModel = hiltViewModel(),
    taskList: MutableList<TaskList>,
    logList: MutableList<LogList>
) {
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val tokenVerificationState = tokenVerificationViewModel.state.value
    val locationState = locationViewModel.state.value
    val listFieldsState = listFieldsViewModel.state.value

    var isTokenValid: Boolean by remember {
        mutableStateOf(false)
    }

    var hasToken: String? by remember {
        mutableStateOf(null)
    }

    var listPropertiesError: String by remember {
        mutableStateOf("")
    }

    var weather: WeatherResponse? by remember {
        mutableStateOf(null)
    }

    var properties: List<ListPropertiesResponse> by remember {
        mutableStateOf(listOf())
    }

    var weatherLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var propertiesLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var modalVisible: Boolean by remember {
        mutableStateOf(false)
    }

    val fieldsMap = remember {
        mutableStateMapOf<String, List<FieldResponse>>()
    }

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

    LaunchedEffect(true) {
        if (!tokenViewModel.getToken("latitude")
                .isNullOrEmpty() && !tokenViewModel.getToken("longitude").isNullOrEmpty()
        ) {
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

    LaunchedEffect(weatherState.isLoading) {
        weatherLoading = weatherState.isLoading
    }

    LaunchedEffect(weatherState.response) {
        weatherState.response?.let {
            Log.d("WEATHER", it.toString())
            weather = weatherState.response
        }
    }

    LaunchedEffect(tokenVerificationState.response) {
        Log.d("RESPONSE", tokenVerificationState.response?.decodedToken ?: "sem resposta")

        tokenVerificationState.response?.let {
            tokenViewModel.saveToken("verifiedToken", it.success.toString())
            isTokenValid = it.success

            listPropertiesViewModel.listProperties()
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        Log.d("ERROR", tokenVerificationState.error)

        if (tokenVerificationState.error.isNotEmpty()) {
            tokenViewModel.clearAll()
            navigationController.navigate("Login")
        }
    }

    LaunchedEffect(listPropertiesState.isLoading) {
        propertiesLoading = listPropertiesState.isLoading
    }

    LaunchedEffect(listPropertiesState.response) {
        listPropertiesState.response?.let {
            Log.d("PROPERTIES", it.toString())
            properties = listPropertiesState.response
        }
    }

    LaunchedEffect(listPropertiesState.error) {
        listPropertiesError = listPropertiesState.error
    }

    LaunchedEffect(listPropertiesState.response) {
        listPropertiesState.response?.let {
            properties.forEach { property ->
                Log.d("NAME", property.name)
                listFieldsViewModel.listFields(property.id)
            }
        }
    }

    LaunchedEffect(listFieldsState.response) {
        listFieldsState.response.forEach { (propertyId, fieldsList) ->
            fieldsMap[propertyId] = fieldsList
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))

                    Header(
                        nome = "Gilmar",
                        navigationController = navigationController,
                        onItemClick = {
                            modalVisible = true
                        }
                    )

                    if (modalVisible) {
                        Modal(
                            onConfirm = {
                                tokenViewModel.clearAll()
                                navigationController.navigate("Login")
                            },
                            onDismiss = {
                                modalVisible = false
                            },
                            icon = R.drawable.baseline_logout_24,
                            title = "Tem certeza que\n deseja sair?",
                            description = "",
                            onConfirmText = "Sair",
                            onDismissText = "Cancelar",
                            space = 0.dp
                        )
                    }
                }

                item {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.linhas),
                        contentDescription = "Linhas que separam o conteúdo",
                        modifier = Modifier
                            .padding(
                                top = 20.dp,
                                bottom = 40.dp
                            )
                    )
                }

                when {
                    propertiesLoading -> {
                        item {
                            ListPropertiesHomeSkeleton()
                        }
                    }

                    properties.isNotEmpty() -> {
                        item {
                            Carousel(
                                isLoading = weatherLoading,
                                weather = weather,
                                temperature = weather?.main?.temp ?: 0.0,
                                description = weather?.weather?.get(0)?.description ?: "",
                                iconUrl = "https://openweathermap.org/img/wn/${
                                    weather?.weather?.get(
                                        0
                                    )?.icon
                                }@2x.png",
                                onClick = {
                                    weatherViewModel.getCurrentWeather()
                                }
                            )
                        }

                        items(
                            count = properties.size,
                            key = {
                                properties[it].id
                            }
                        ) { index ->
                            Property(
                                navigationController = navigationController,
                                titulo = properties[index].name,
                                propertyId = properties[index].id,
                                producerId = properties[index].producerId,
                                fields = fieldsMap[properties[index].id] ?: listOf(),
                                fieldsState = listFieldsState,
                                withSpacing = index != properties.size - 1
                            )
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 30.dp,
                                        vertical = 60.dp
                                    )
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    texto = "Nova Propriedade",
                                    modifier = Modifier
                                        .padding(vertical = 18.dp, horizontal = 15.dp),
                                    hasIcon = true,
                                    shape = ShapeProperty.small,
                                    onClick = {
                                        navigationController.navigate("NewProperty")
                                    },
                                    backgroundColor = VERDE_CLARO,
                                    contentColor = BRANCO,
                                    defaultElevetion = 3.dp
                                )
                            }
                        }

                        item {
                            RecentActivities(
                                title = "Atividades recentes",
                                list = logList
                            )
                        }

                        item {
                            Tasks(
                                titulo = "Lista de tarefas",
                                subTitulo = "Seus afazeres da semana!",
                                navigationController = navigationController,
                                taskList = taskList
                            )
                        }

                    }

                    listPropertiesError.isNotEmpty() -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .padding(top = 60.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 30.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.listpropertieserroricon),
                                    contentDescription = "Imagem representativa de um produtor"
                                )

                                NoData(
                                    text = "Não foi possivel carregar\nsuas propriedades...",
                                    buttonValue = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {
                                        listPropertiesViewModel.listProperties()
                                    }
                                )
                            }

                        }
                    }

                    else -> {
                        item {
                            Spacer(modifier = Modifier.height(60.dp))

                            NoData(
                                text = "Ainda não há uma\npropriedade cadastrada!",
                                buttonValue = "Nova Propriedade",
                                onClick = {
                                    navigationController.navigate("NewProperty")
                                }
                            )
                        }
                    }
                }
            }
        } else {
            HomeSkeleton(
                padding = innerPadding.calculateTopPadding()
            )
        }
    }
}