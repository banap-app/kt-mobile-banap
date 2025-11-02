package com.banap.banap.app.presentation.home.ui.engineer.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.producer.information.components.CreateDetails
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.home.ui.components.Activities
import com.banap.banap.app.presentation.home.ui.components.AddNewCard
import com.banap.banap.app.presentation.home.ui.components.Carousel
import com.banap.banap.app.presentation.home.ui.components.ClientCard
import com.banap.banap.app.presentation.home.ui.components.Menu
import com.banap.banap.app.presentation.home.ui.components.Notifications
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.home.ui.components.TitleSection
import com.banap.banap.app.presentation.home.ui.components.ToolsCard
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.components.ClientFieldInformationAnalysisSectionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.engineerHome.ListClientsHomeSkeleton
import com.banap.banap.app.presentation.skeleton.ui.home.components.LogListSkeleton
import com.banap.banap.app.presentation.skeleton.ui.home.screen.HomeSkeleton
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERMELHO
import com.banap.banap.core.ui.util.getFirstName
import com.banap.banap.core.ui.util.setColorInText
import com.banap.banap.data.model.menu.DropDownItem
import com.banap.banap.data.model.menu.MenuOption
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.domain.viewmodel.engineer.GetEngineerByIdViewModel
import com.banap.banap.domain.viewmodel.location.LocationViewModel
import com.banap.banap.domain.viewmodel.token.TokenVerificationViewModel
import com.banap.banap.domain.viewmodel.weather.WeatherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EngineerHome(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    tokenVerificationViewModel: TokenVerificationViewModel,
    locationViewModel: LocationViewModel,
    weatherViewModel: WeatherViewModel,
    getEngineerByIdViewModel: GetEngineerByIdViewModel,
    logList: MutableList<LogList>
) {
    // Variaveis do Scaffold

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Estados da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
    }

    // Variaveis utilizadas dentro do conteudo

    // GetCurrentLocation - Pedir permissão para poder pegar localização atual

    val locationState by locationViewModel.state

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            locationViewModel.getCurrentLocation()
        } else {
            scope.launch {
                val autoDismissJob = launch {
                    delay(5_000L)
                    weatherViewModel.getCurrentWeather()
                    snackBarHostState.currentSnackbarData?.dismiss()
                }

                val result = snackBarHostState.showSnackbar(
                    message = "Usaremos um valor padrão para mostrar o clima!",
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )

                if (result == SnackbarResult.ActionPerformed) {
                    weatherViewModel.getCurrentWeather()
                }

                autoDismissJob.cancel()
            }
        }
    }

    LaunchedEffect(locationState.response) {
        locationState.response?.let {
            weatherViewModel.getCurrentWeather(
                latitude = it.latitude,
                longitude = it.longitude
            )

            tokenViewModel.saveTokens(
                mapOf(
                    "latitude" to it.latitude.toString(),
                    "longitude" to it.longitude.toString()
                )
            )
        }
    }

    // Token Verification - Verificando e validando o TOKEN

    val tokenVerificationState by tokenVerificationViewModel.state

    var tokenVerificationLoading: Boolean by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("crea")?.let {
            tokenViewModel.clearToken("password")
        }

        tokenViewModel.getToken("password")?.let {
            tokenViewModel.clearToken("crea")
        }

        tokenViewModel.getToken("token")?.let {
            tokenVerificationViewModel.verifyToken(it)
        } ?: run {
//            tokenViewModel.clearAll()
//            navigationController.navigate("Login")
        }
    }

    LaunchedEffect(tokenVerificationState.response) {
        tokenVerificationState.response?.let {
            if (it.success) {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                getEngineerByIdViewModel.getEngineerById()
            }
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        if (tokenVerificationState.error.isNotEmpty()) {
            hasContentError = "tokenVerificationError: ${tokenVerificationState.error}"
        }
    }

    LaunchedEffect(tokenVerificationState.isLoading) {
        tokenVerificationLoading = tokenVerificationState.isLoading
    }

    // Weather - Clima

    val weatherState by weatherViewModel.state

    var weatherLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var weatherContent: WeatherResponse? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("latitude")?.let { latitude ->
            tokenViewModel.getToken("longitude")?.let { longitude ->
                weatherViewModel.getCurrentWeather(
                    latitude = latitude.toDouble(),
                    longitude = longitude.toDouble()
                )
            }
        }
    }

    LaunchedEffect(weatherState.response) {
        weatherState.response?.let {
            weatherContent = weatherState.response
        }
    }

    LaunchedEffect(weatherState.isLoading) {
        weatherLoading = weatherState.isLoading
    }

    // Pegar Engenheiro pelo ID

    val getEngineerByIdState = getEngineerByIdViewModel.state.value

    var engineerName: String by remember {
        mutableStateOf("usuario")
    }

    var engineerLoading: Boolean by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(getEngineerByIdState.response) {
        getEngineerByIdState.response?.let {
            if (it.statusCode == 200) {
                engineerName = it.data?.name.toString()
                tokenViewModel.saveToken("engineerName", it.data?.name.toString())
            }
        }
    }

    LaunchedEffect(getEngineerByIdState.errorMessage) {
        getEngineerByIdState.errorMessage?.let {
            hasContentError = "engineerError: $it"
        }
    }

    LaunchedEffect(getEngineerByIdState.isLoading) {
        engineerLoading = getEngineerByIdState.isLoading
    }

    // Modal

    var isHeaderMenuVisible: Boolean by remember {
        mutableStateOf(false)
    }

    // Clientes

    var clients: List<String> by remember {
        mutableStateOf(
            listOf(
                "Lucas",
                "Asher",
                "Victor",
                "Gilmar"
            )
        )
    }

    var clientsLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var hasClientsError: String by remember {
        mutableStateOf("")
    }

    // Ferramentas

    var tools: List<String> by remember {
        mutableStateOf(
            listOf(
                "Calagem",
                "N.P.K",
                "Escanear"
            )
        )
    }

    // LogList

    var logListTest: List<String> by remember {
        mutableStateOf(
            listOf(
                "Lucas",
                "Asher",
                "Victor",
                "Gilmar"
            )
        )
    }

    var logListLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var hasLogListError: String by remember {
        mutableStateOf("")
    }

    // Analises

    var analysis: List<String> by remember {
        mutableStateOf(
            listOf(
                "Análise 01",
                "Análise 02",
                "Análise 03"
            )
        )
    }

    var analysisLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var hasAnalysisError: String by remember {
        mutableStateOf("")
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 20.dp
                )
        ) {
            when {
                tokenVerificationLoading || engineerLoading -> {
                    item {
                        HomeSkeleton(
                            padding = innerPadding.calculateTopPadding(),
                            isEngineerHome = true
                        )
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = setColorInText(
                                    texto = "Olá, ",
                                    textoASerDestacado = "usuário!",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    corEmDestaque = VERDE_CLARO,
                                    ordemInversa = false
                                ),
                                style = Typography.headlineSmall,
                                color = PRETO
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Notifications(
                                    error = hasContentError
                                )

                                Menu(
                                    dropDownItems = listOf(
                                        DropDownItem(
                                            option = MenuOption(
                                                icon = R.drawable.baseline_logout_24,
                                                text = "Sair"
                                            ),
                                            optionSelected = {
                                                isHeaderMenuVisible = true
                                            }
                                        ),
                                        DropDownItem(
                                            option = MenuOption(
                                                icon = R.drawable.fieldiconedit,
                                                text = "Editar"
                                            ),
                                            optionSelected = {}
                                        )
                                    )
                                )
                            }
                        }

                        if (isHeaderMenuVisible) {
                            Modal(
                                onConfirm = {
                                    tokenViewModel.clearAll()
                                    navigationController.navigate("Login")
                                },
                                onDismiss = {
                                    isHeaderMenuVisible = false
                                },
                                icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                                iconColor = VERMELHO,
                                title = "Tem certeza que\n deseja sair?",
                                description = "",
                                onConfirmText = "Sair",
                                onConfirmButtonBackgroundColor = VERMELHO,
                                onConfirmButtonContentColor = BRANCO,
                                onDismissText = "Cancelar",
                                onDismissButtonBackgroundColor = CINZA_INTERMEDIARIO,
                                onDismissButtonContentColor = PRETO,
                                space = 40.dp
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

                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.7f),
                            text = if (hasContentError.contains("tokenVerificationError")) "Sua sessão expirou!\nSuas credenciais estao incorretas.\nLogue novamente." else "Ocorreu um erro...",
                            buttonText = if (hasContentError.contains("tokenVerificationError")) "Sair" else "Tentar Novamente",
                            buttonBackgroundColor = VERMELHO,
                            icon = if (hasContentError.contains("tokenVerificationError")) ImageVector.vectorResource(
                                id = R.drawable.baseline_logout_24
                            ) else ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {
                                if (hasContentError.contains("tokenVerificationError")) {
                                    tokenViewModel.clearAll()
                                    navigationController.navigate("Login")
                                } else {
                                    getEngineerByIdViewModel.getEngineerById()
                                }
                            }
                        )
                    }
                }

                else -> {
                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 30.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = setColorInText(
                                    texto = "Olá, ",
                                    textoASerDestacado = "${
                                        getFirstName(
                                            tokenViewModel.getToken(
                                                "engineerName"
                                            ) ?: engineerName
                                        )
                                    }!",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    corEmDestaque = VERDE_CLARO,
                                    ordemInversa = false
                                ),
                                style = Typography.headlineSmall,
                                color = PRETO
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Notifications()

                                Menu(
                                    dropDownItems = listOf(
                                        DropDownItem(
                                            option = MenuOption(
                                                icon = R.drawable.baseline_logout_24,
                                                text = "Sair"
                                            ),
                                            optionSelected = {
                                                isHeaderMenuVisible = true
                                            }
                                        ),
                                        DropDownItem(
                                            option = MenuOption(
                                                icon = R.drawable.fieldiconedit,
                                                text = "Editar"
                                            ),
                                            optionSelected = {
                                                navigationController.navigate(
                                                    "UpdateUserInformation"
                                                )
                                            }
                                        )
                                    )
                                )
                            }
                        }

                        if (isHeaderMenuVisible) {
                            Modal(
                                onConfirm = {
                                    tokenViewModel.clearAll()
                                    navigationController.navigate("Login")
                                },
                                onDismiss = {
                                    isHeaderMenuVisible = false
                                },
                                icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                                iconColor = VERMELHO,
                                title = "Tem certeza que\n deseja sair?",
                                description = "",
                                onConfirmText = "Sair",
                                onConfirmButtonBackgroundColor = VERMELHO,
                                onConfirmButtonContentColor = BRANCO,
                                onDismissText = "Cancelar",
                                onDismissButtonBackgroundColor = CINZA_INTERMEDIARIO,
                                onDismissButtonContentColor = PRETO,
                                space = 40.dp
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

                    item {
                        Carousel(
                            isLoading = weatherLoading,
                            weather = weatherContent,
                            temperature = weatherContent?.main?.temp ?: 0.0,
                            description = weatherContent?.weather?.get(0)?.description ?: "",
                            iconUrl = "https://openweathermap.org/img/wn/${
                                weatherContent?.weather?.get(
                                    0
                                )?.icon
                            }@2x.png",
                            onClick = {
                                weatherViewModel.getCurrentWeather()
                            }
                        )
                    }

                    item {
                        TitleSection(
                            "Clientes",
                            isRowList = false,
                            onClickClickableText = {
                                navigationController.navigate(
                                    "Clients"
                                )
                            },
                            onClickArrowButton = {
                                navigationController.navigate(
                                    "Clients"
                                )
                            }
                        )
                    }

                    when {
                        clientsLoading -> {
                            item {
                                ListClientsHomeSkeleton()
                            }
                        }

                        hasClientsError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            bottom = 60.dp
                                        ),
                                    text = "Ocorreu um erro \nao carregar seus clientes...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        else -> {
                            item {
                                LazyRow(
                                    modifier = Modifier
                                        .padding(
                                            bottom = 60.dp
                                        )
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 25.dp
                                    )
                                ) {
                                    items(
                                        count = clients.take(5).size,
                                        key = {
                                            clients[it]
                                        }
                                    ) {
                                        ClientCard(
                                            name = clients[it],
                                            hasPaddingStart = it == 0,
                                            onClick = {
                                                navigationController.navigate("ClientInformation")
                                            }
                                        )
                                    }

                                    item {
                                        AddNewCard(
                                            height = 122.dp,
                                            width = 177.dp,
                                            icon = ImageVector.vectorResource(id = R.drawable.addclient),
                                            isEngineerHome = true,
                                            onClick = {
                                                navigationController.navigate(
                                                    "ClientAggregation"
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(
                                    bottom = 60.dp
                                )
                                .fillMaxWidth()
                        ) {
                            TitleSection(
                                "Ferramentas",
                                isRowList = false,
                                onClickClickableText = {
                                    navigationController.navigate("Tools")
                                },
                                onClickArrowButton = {
                                    navigationController.navigate("Tools")
                                }
                            )

                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 15.dp
                                )
                            ) {
                                items(
                                    count = tools.size,
                                    key = {
                                        tools[it]
                                    }
                                ) {
                                    ToolsCard(
                                        name = tools[it],
                                        icon =
                                        when (tools[it]) {
                                            "Calagem" -> R.drawable.analysisiconliming
                                            "N.P.K" -> R.drawable.analysisiconnpk
                                            "Escanear" -> R.drawable.baseline_crop_free_24
                                            else -> R.drawable.homeiconretry
                                        },
                                        hasPaddingStart = it == 0,
                                        hasPaddingEnd = it == tools.size - 1,
                                        onClick = {
                                            when (tools[it]) {
                                                "Calagem" -> {
                                                    navigationController.navigate("NewEngineerLimingAnalysis")
                                                }

                                                "N.P.K" -> {
                                                    navigationController.navigate("NewEngineerFertilizationRecommendationAnalysis")
                                                }

                                                "Escanear" -> {
                                                    navigationController.navigate("Scanner")
                                                }

                                                else -> {}
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        TitleSection(
                            title = "Atividades recentes",
                            isRowList = false,
                            hasArrowButton = false
                        )
                    }

                    when {
                        logListLoading -> {
                            item {
                                LogListSkeleton()
                            }
                        }

                        hasLogListError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            top = 20.dp
                                        ),
                                    text = "Ocorreu um erro ao carregar\n as atividades recentes...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        logListTest.isEmpty() -> {
                            item {
                                Activities(
                                    icone = Icons.Outlined.Info,
                                    autor = "Você",
                                    atividade = "não realizou nenhuma atividade recentemente.",
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 30.dp
                                        )
                                )
                            }
                        }

                        else -> {
                            items(
                                count = logListTest.take(5).size,
                                key = {
                                    logListTest[it]
                                }
                            ) {
                                Activities(
                                    autor = logListTest[it],
                                    atividade = when (logListTest[it]) {
                                        "Lucas" -> "realizou uma análise de npk."
                                        "Asher" -> "realizou uma análise de calagem."
                                        "Victor" -> "cadastrou uma propriedade."
                                        "Gilmar" -> "agregou um cliente."
                                        else -> ""
                                    },
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = if ((it + 1) == logListTest.size) 20.dp else 10.dp
                                        )
                                )
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "10 de Maio 2025 ás 17:54",
                                        style = Typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CINZA_ESCURO
                                    )
                                }
                            }
                        }
                    }

                    item {
                        TitleSection(
                            title = "Análises",
                            isRowList = false,
                            hasSpaceTop = true,
                            hasArrowButton = analysis.isNotEmpty(),
                            onClickClickableText = {
                                navigationController.navigate(
                                    "Analysis"
                                )
                            },
                            onClickArrowButton = {
                                navigationController.navigate(
                                    "Analysis"
                                )
                            }
                        )
                    }

                    when {
                        analysisLoading -> {
                            item {
                                ClientFieldInformationAnalysisSectionSkeleton()
                            }
                        }

                        hasAnalysisError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            bottom = 60.dp
                                        ),
                                    text = "Ocorreu um erro\nao carregar as análises...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        analysis.isEmpty() -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = 60.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Você ainda não realizou nenhuma análise!",
                                        style = Typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = CINZA_ESCURO
                                    )
                                }
                            }
                        }

                        else -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = 20.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    CreateDetails(
                                        username = "Victor",
                                        createdAt = "17 Maio 2024 ás 20:56"
                                    )
                                }
                            }

                            items(
                                count = analysis.size,
                                key = {
                                    analysis[it]
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            top = 5.dp,
                                            bottom = if (it == (analysis.size - 1)) 60.dp else 5.dp,
                                            start = 30.dp,
                                            end = 30.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    ListItemCard(
                                        modifier = Modifier
                                            .height(120.dp)
                                            .widthIn(
                                                max = 290.dp
                                            ),
                                        onClick = {},
                                        title = "Análise",
                                        titleStyle = Typography.labelSmall,
                                        nameStyle = Typography.titleMedium,
                                        name = "Análise " + (if (((analysis.size - it)) < 10) "0${analysis.size - it}" else analysis.size - it)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(end = 30.dp),
                                            horizontalArrangement = Arrangement.spacedBy(
                                                space = 15.dp,
                                                alignment = Alignment.CenterHorizontally
                                            )
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 5.dp
                                                ),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "1+",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = BRANCO
                                                )

                                                Icon(
                                                    imageVector = ImageVector.vectorResource(
                                                        id = R.drawable.analysisiconliming
                                                    ),
                                                    contentDescription = "Icone de Calagem",
                                                    modifier = Modifier
                                                        .scale(1.3f)
                                                )
                                            }

                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 5.dp
                                                ),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "1+",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = BRANCO
                                                )

                                                Icon(
                                                    imageVector = ImageVector.vectorResource(
                                                        id = R.drawable.analysisiconnpk
                                                    ),
                                                    contentDescription = "Icone de Npk",
                                                    modifier = Modifier
                                                        .scale(1.3f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}