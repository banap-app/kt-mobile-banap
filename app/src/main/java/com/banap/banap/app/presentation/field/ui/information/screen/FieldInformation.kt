package com.banap.banap.app.presentation.field.ui.information.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.analysis.ui.information.components.CreateDetails
import com.banap.banap.app.presentation.field.ui.information.components.FieldActions
import com.banap.banap.app.presentation.home.ui.components.TaskCard
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.field.screen.FieldInformationSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.ImageInformation
import com.banap.banap.core.ui.components.Information
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TitleInformation
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO
import com.banap.banap.core.ui.util.ISOConverter
import com.banap.banap.core.ui.util.getFirstName
import com.banap.banap.core.ui.util.shimmerEffect
import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.domain.viewmodel.analysis.ListAnalysisViewModel
import com.banap.banap.domain.viewmodel.field.DeleteFieldViewModel
import com.banap.banap.domain.viewmodel.field.GetFieldByIdViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FieldInformation(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    getFieldByIdViewModel: GetFieldByIdViewModel = hiltViewModel(),
    deleteFieldViewModel: DeleteFieldViewModel = hiltViewModel(),
    fieldId: String,
    userName: String? = null,
    listAnalysisViewModel: ListAnalysisViewModel,
    taskList: MutableList<String>
) {
    val context = LocalContext.current

    val deleteFieldState = deleteFieldViewModel.state.value
    val getFieldByIdState = getFieldByIdViewModel.state.value
    val listAnalysisState = listAnalysisViewModel.state.value

    var modalVisible: Boolean by remember {
        mutableStateOf(false)
    }

    var analysisList: List<AnalysisResponse> by remember {
        mutableStateOf(listOf())
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isLoadingDelete by remember {
        mutableStateOf(false)
    }

    var isLoadingAnalysisList by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    var errorDelete by remember {
        mutableStateOf("")
    }

    var errorAnalysisList by remember {
        mutableStateOf("")
    }

    var field: FieldResponse? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(context) {
        if (userName?.contains("userName") == false) {
            tokenViewModel.saveToken("userName", userName)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.clearToken("analysisId")
        Log.d("field", tokenViewModel.getToken("fieldId") ?: "")

        if (tokenViewModel.getToken("fieldId").isNullOrEmpty()) {
            getFieldByIdViewModel.getFieldById(fieldId)
            listAnalysisViewModel.listAnalysis(fieldId)
        } else {
            getFieldByIdViewModel.getFieldById(tokenViewModel.getToken("fieldId") ?: "")
            listAnalysisViewModel.listAnalysis(tokenViewModel.getToken("fieldId") ?: "")
        }
    }

    LaunchedEffect(getFieldByIdState.isLoading) {
        isLoading = getFieldByIdState.isLoading
    }

    LaunchedEffect(getFieldByIdState.response) {
        getFieldByIdState.response?.let {
            tokenViewModel.saveToken("fieldId", getFieldByIdState.response.id)
            Log.d("field", tokenViewModel.getToken("fieldId") ?: "")
            field = getFieldByIdState.response
        }
    }

    LaunchedEffect(getFieldByIdState.error) {
        error = getFieldByIdState.error
    }

    LaunchedEffect(deleteFieldState.isLoading) {
        isLoadingDelete = deleteFieldState.isLoading
    }

    LaunchedEffect(deleteFieldState.response) {
        deleteFieldState.response?.let {
            if (it.success) {
                navigationController.navigate("Home")
            }
        }
    }

    LaunchedEffect(deleteFieldState.error) {
        errorDelete = deleteFieldState.error
    }

    LaunchedEffect(listAnalysisState.isLoading) {
        isLoadingAnalysisList = listAnalysisState.isLoading
    }

    LaunchedEffect(listAnalysisState.response) {
        listAnalysisState.response?.let {
            analysisList = listAnalysisState.response.analysis
            Log.d("launched analysis", "launched analysis: $analysisList")
        }
    }

    LaunchedEffect(listAnalysisState.error) {
        errorAnalysisList = listAnalysisState.error
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) {
        if (!isLoadingDelete) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    RegistrationHeader(
                        navigationController = navigationController,
                        fixedRoute = "Home"
                    )
                }

                when {
                    isLoading -> {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .fillMaxWidth(0.4f)
                                        .height(30.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )
                            }
                        }
                    }

                    else -> {
                        item {
                            TitleInformation(
                                title = field?.name ?: "",
                                icon = R.drawable.field
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(40.dp))
                }

                when {
                    field != null -> {
                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 20.dp
                                )
                            ) {
                                ImageInformation(
                                    image = R.drawable.fieldimage,
                                    icon = R.drawable.fieldiconplant,
                                    text = field?.crop ?: "",
                                    child = {
                                        Text(
                                            text = "10",
                                            style = Typography.bodyLarge,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = VERDE_CLARO
                                        )
                                    }
                                )

                                FieldActions(
                                    navigationController,
                                    onClickDelete = {
                                        modalVisible = true
                                    },
                                    onClickEdit = {}
                                )

                                if (modalVisible) {
                                    Modal(
                                        onConfirm = {
                                            deleteFieldViewModel.deleteField(
                                                tokenViewModel.getToken("fieldId") ?: fieldId
                                            )
                                            modalVisible = false
                                        },
                                        onDismiss = {
                                            modalVisible = false
                                        },
                                        icon = ImageVector.vectorResource(id = R.drawable.fieldicondelete),
                                        iconColor = VERMELHO,
                                        title = "Tem certeza que deseja\n apagar o talhão?",
                                        description = "Apagando o talhão, todas as informações relacionadas a ele tambem serão apagadas!",
                                        onConfirmText = "Excluir",
                                        onConfirmButtonBackgroundColor = VERMELHO,
                                        onConfirmButtonContentColor = BRANCO,
                                        onDismissText = "Cancelar",
                                        onDismissButtonBackgroundColor = CINZA_INTERMEDIARIO,
                                        onDismissButtonContentColor = PRETO,
                                        space = 0.dp
                                    )
                                }
                            }
                        }

                        item {
                            Information(
                                paddingTop = 60.dp,
                                icon = R.drawable.fieldicondescription,
                                title = "Descrição",
                                child = {
                                    Text(
                                        text = field?.description ?: "",
                                        style = Typography.bodyLarge,
                                        color = PRETO,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            )
                        }

                        item {
                            Information(
                                space = if (taskList.isEmpty()) 60.dp else 40.dp,
                                paddingTop = 60.dp,
                                icon = R.drawable.fieldicontask,
                                title = "Lista de Tarefas",
                                child = {
                                    when {
                                        taskList.isEmpty() -> {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 40.dp,
                                                    alignment = Alignment.CenterVertically
                                                )
                                            ) {
                                                Text(
                                                    text = "Você ainda não tem\numa tarefa á ser feita!",
                                                    textAlign = TextAlign.Center,
                                                    style = Typography.bodyLarge,
                                                    fontWeight = FontWeight.Bold,
                                                    color = CINZA_ESCURO
                                                )

                                                Button(
                                                    texto = "Nova Tarefa",
                                                    modifier = Modifier
                                                        .padding(
                                                            vertical = 18.dp,
                                                            horizontal = 15.dp
                                                        ),
                                                    hasIcon = true,
                                                    shape = ShapeProperty.small,
                                                    onClick = {
                                                        navigationController.navigate("NewTask")
                                                    },
                                                    backgroundColor = VERDE_CLARO,
                                                    contentColor = BRANCO,
                                                    defaultElevetion = 3.dp
                                                )
                                            }
                                        }

                                        else -> {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 60.dp
                                                )
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    verticalArrangement = Arrangement.spacedBy(
                                                        space = 10.dp
                                                    )
                                                ) {
                                                    taskList.forEach { task ->
                                                        TaskCard(
                                                            cardColor = VERMELHO,
                                                            priority = "Alta",
                                                            name = task,
                                                            time = "00:60 ás 08:00"
                                                        )
                                                    }
                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.End
                                                ) {
                                                    Button(
                                                        texto = "Nova Tarefa",
                                                        modifier = Modifier
                                                            .padding(
                                                                vertical = 18.dp,
                                                                horizontal = 15.dp
                                                            ),
                                                        hasIcon = true,
                                                        shape = ShapeProperty.small,
                                                        onClick = {
                                                            navigationController.navigate("NewTask")
                                                        },
                                                        backgroundColor = VERDE_CLARO,
                                                        contentColor = BRANCO,
                                                        defaultElevetion = 3.dp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        item {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        top = 60.dp,
                                        start = 30.dp,
                                        end = 30.dp
                                    )
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 5.dp
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 10.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.fieldicontroubleshoot),
                                        contentDescription = "Icone de Informação",
                                        tint = VERDE_ESCURO
                                    )

                                    Text(
                                        text = "Ultimas Análises",
                                        style = Typography.titleMedium,
                                        color = VERDE_ESCURO,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 30.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Listadas abaixo estão as ultímas 5 análises feitas nesse talhão!",
                                    style = Typography.bodyLarge,
                                    color = VERDE_ESCURO,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }

                        when {
                            analysisList.isEmpty() -> {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .padding(vertical = 60.dp)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            space = 40.dp,
                                            alignment = Alignment.CenterVertically
                                        )
                                    ) {
                                        Text(
                                            text = "Você ainda não\nrealizou nenhuma análise!",
                                            textAlign = TextAlign.Center,
                                            style = Typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = CINZA_ESCURO
                                        )

                                        Button(
                                            texto = "Nova Análise",
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 18.dp,
                                                    horizontal = 15.dp
                                                ),
                                            hasIcon = true,
                                            shape = ShapeProperty.small,
                                            onClick = {
                                                navigationController.navigate("NewLimingCalculation")
                                            },
                                            backgroundColor = VERDE_CLARO,
                                            contentColor = BRANCO,
                                            defaultElevetion = 3.dp
                                        )
                                    }
                                }
                            }

                            else -> {
                                val lastAnalysis = analysisList.sortedBy { it.createdAt }.takeLast(3).asReversed()

                                item {
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                top = 40.dp,
                                                start = 30.dp,
                                                end = 30.dp,
                                                bottom = 20.dp
                                            )
                                            .fillMaxWidth()
                                    ) {
                                        CreateDetails(
                                            username = tokenViewModel.getToken("userName")
                                                ?.let { it1 -> getFirstName(it1) },
                                            createdAt = ISOConverter(analysisList.last().createdAt)
                                        )
                                    }
                                }

                                items(
                                    count = lastAnalysis.size,
                                    key = {
                                        lastAnalysis[it].id
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                vertical = 5.dp,
                                                horizontal = 30.dp
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
                                            onClick = {
                                                navigationController.navigate(
                                                    Screen.AnalysisInformation.createRoute(
                                                        analysisId = lastAnalysis[it].id,
                                                        analysisName = "Análise " + (if (((analysisList.size - it)) < 11) "0${analysisList.size - it}" else analysisList.size - it),
                                                        fieldName = field?.name ?: "",
                                                    )
                                                )
                                            },
                                            title = "Análise",
                                            titleStyle = Typography.labelSmall,
                                            nameStyle = Typography.titleMedium,
                                            name = "Análise " + (if (((analysisList.size - it)) < 10) "0${analysisList.size - it}" else analysisList.size - it)
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

                                item {
                                    Row (
                                        modifier = Modifier
                                            .padding(
                                                top = 55.dp,
                                                start = 30.dp,
                                                end = 30.dp,
                                                bottom = 60.dp
                                            )
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Button(
                                            texto = "Nova Análise",
                                            modifier = Modifier
                                                .padding(
                                                    vertical = 18.dp,
                                                    horizontal = 15.dp
                                                ),
                                            hasIcon = true,
                                            shape = ShapeProperty.small,
                                            onClick = {
                                                navigationController.navigate("NewLimingCalculation")
                                            },
                                            backgroundColor = VERDE_CLARO,
                                            contentColor = BRANCO,
                                            defaultElevetion = 3.dp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    isLoading -> {
                        item {
                            FieldInformationSkeleton()
                        }
                    }
                }
            }
        } else {
            LoadingScreen()
        }
    }
}