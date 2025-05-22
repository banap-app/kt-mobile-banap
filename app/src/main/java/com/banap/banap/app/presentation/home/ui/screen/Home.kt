package com.banap.banap.app.presentation.home.ui.screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.home.ui.components.Carousel
import com.banap.banap.app.presentation.home.ui.components.Header
import com.banap.banap.app.presentation.home.ui.components.Property
import com.banap.banap.app.presentation.home.ui.components.RecentActivities
import com.banap.banap.app.presentation.home.ui.components.Tasks
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.home.components.ListPropertiesHomeSkeleton
import com.banap.banap.app.presentation.skeleton.ui.home.screen.HomeSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.shimmerEffect
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.TaskList
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.domain.model.property.ListPropertiesState
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
    locationViewModel: LocationViewModel,
    listPropertiesViewModel: ListPropertiesViewModel,
    taskList: MutableList<TaskList>,
    logList: MutableList<LogList>,
    fieldList: MutableList<String>
) {
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val tokenVerificationState = tokenVerificationViewModel.state.value
    val weatherState = weatherViewModel.state.value
    val locationState = locationViewModel.state.value
    val listPropertiesState = listPropertiesViewModel.state.value

    var isTokenValid: Boolean by remember {
        mutableStateOf(false)
    }

    var hasToken: String? by remember {
        mutableStateOf(null)
    }

    var weather: WeatherResponse? by remember {
        mutableStateOf(null)
    }

    var properties: List<ListPropertiesResponse>? by remember {
        mutableStateOf(null)
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

            if (it.success) {
                listPropertiesViewModel.listProperties()
            }
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
        Log.d("ERROR", listPropertiesState.error)
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
                modifier =
                if (properties?.isNotEmpty() == true) {
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .padding(top = 40.dp, bottom = 60.dp)
                } else {
                    Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp, bottom = 60.dp)
                }
            ) {
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

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas),
                    contentDescription = "Linhas que separam o conteúdo",
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            bottom = 40.dp
                        )
                )

                when {
                    propertiesLoading -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 30.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 60.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 20.dp
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .fillMaxWidth()
                                        .height(156.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .width(64.dp)
                                        .height(8.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 35.dp
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )

                                Row(
                                    modifier = Modifier
                                        .horizontalScroll(
                                            state = scrollState,
                                            enabled = false
                                        )
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 25.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeProperty.medium
                                            )
                                            .height(178.dp)
                                            .width(124.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeProperty.medium
                                            )
                                            .height(178.dp)
                                            .width(124.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeProperty.medium
                                            )
                                            .height(178.dp)
                                            .width(124.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeProperty.medium
                                        )
                                        .height(60.dp)
                                        .fillMaxWidth(0.7f)
                                        .shimmerEffect(),
                                    content = {}
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 20.dp
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 10.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeCarousel.medium
                                            )
                                            .fillMaxWidth(0.7f)
                                            .height(15.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeCarousel.medium
                                            )
                                            .fillMaxWidth(0.5f)
                                            .height(15.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeCarousel.medium
                                            )
                                            .fillMaxWidth(0.8f)
                                            .height(15.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .fillMaxWidth(0.4f)
                                        .height(10.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 40.dp
                                )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 5.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeCarousel.medium
                                            )
                                            .fillMaxWidth()
                                            .height(30.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(
                                                shape = ShapeCarousel.medium
                                            )
                                            .fillMaxWidth(0.7f)
                                            .height(20.dp)
                                            .shimmerEffect(),
                                        content = {}
                                    )
                                }

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 60.dp
                                    )
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(
                                            space = 10.dp
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(
                                                    shape = ShapeCarousel.medium
                                                )
                                                .fillMaxWidth(0.5f)
                                                .height(15.dp)
                                                .shimmerEffect(),
                                            content = {}
                                        )

                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(
                                                space = 5.dp
                                            )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(
                                                        shape = ShapeCarousel.medium
                                                    )
                                                    .fillMaxWidth(0.4f)
                                                    .height(10.dp)
                                                    .shimmerEffect(),
                                                content = {}
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .clip(
                                                        shape = ShapeProperty.medium
                                                    )
                                                    .height(50.dp)
                                                    .fillMaxWidth()
                                                    .shimmerEffect(),
                                                content = {}
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(
                                                    shape = ShapeProperty.medium
                                                )
                                                .height(60.dp)
                                                .fillMaxWidth(0.7f)
                                                .shimmerEffect(),
                                            content = {}
                                        )
                                    }
                                }
                            }
                        }
                    }

                    properties?.isNotEmpty() == true -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                space = 60.dp
                            )
                        ) {
                            Carousel(
                                isLoading = weatherLoading,
                                weather = weather,
                                temperature = weather?.main?.temp ?: 0.0,
                                description = weather?.weather?.get(0)?.description ?: "",
                                iconUrl = "https://openweathermap.org/img/wn/${
                                    weather?.weather?.get(
                                        0
                                    )?.icon
                                }@2x.png"
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 60.dp
                                )
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 40.dp
                                    )
                                ) {
                                    when {
                                        propertiesLoading -> {
                                            ListPropertiesHomeSkeleton()
                                        }

                                        else -> {
                                            properties?.forEach {
                                                Property(
                                                    titulo = it.name,
                                                    navigationController = navigationController,
                                                    fieldList = fieldList
                                                )
                                            }
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 30.dp)
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

                            RecentActivities(
                                title = "Atividades recentes",
                                list = logList
                            )

                            Tasks(
                                titulo = "Lista de tarefas",
                                subTitulo = "Seus afazeres da semana!",
                                navigationController = navigationController,
                                fieldList = fieldList,
                                taskList = taskList
                            )
                        }
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 40.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            Text(
                                text = "Ainda não há uma\npropriedade cadastrada!",
                                textAlign = TextAlign.Center,
                                style = Typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = CINZA_ESCURO
                            )

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
                }
            }
        } else {
            HomeSkeleton(
                padding = innerPadding.calculateTopPadding()
            )
        }
    }
}