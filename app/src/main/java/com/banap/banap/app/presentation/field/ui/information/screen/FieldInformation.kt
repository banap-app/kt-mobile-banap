package com.banap.banap.app.presentation.field.ui.information.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.information.components.CreateDetails
import com.banap.banap.app.presentation.field.ui.information.components.FieldActions
import com.banap.banap.app.presentation.home.ui.components.TaskCard
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.ImageInformation
import com.banap.banap.core.ui.components.Information
import com.banap.banap.core.ui.components.InformationScreenPattern
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FieldInformation(
    navigationController: NavController
) {
    val isLoading by remember {
        mutableStateOf(false)
    }

    var modalVisible: Boolean by remember {
        mutableStateOf(false)
    }

    val analysisList = mutableListOf<String>(
        "Análise 01"
    )

    val taskList = mutableListOf<String>(
        "Adubar",
        "Jambrolhar"
    )

    InformationScreenPattern(
        navigationController = navigationController,
        fixedRoute = "Home",
        title = "Talhão 01",
        titleIcon = R.drawable.field
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            ImageInformation(
                image = R.drawable.fieldimage,
                icon = R.drawable.fieldiconplant,
                text = "Banana Nanica",
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
                    onConfirm = {},
                    onDismiss = {
                        modalVisible = false
                    },
                    icon = R.drawable.fieldicondelete,
                    title = "Tem certeza que deseja\n apagar o talhão?",
                    description = "Apagando o talhão, todas as informações relacionadas a ele tambem serão apagadas!",
                    onConfirmText = "Excluir",
                    onDismissText = "Cancelar"
                )
            }
        }

        Information(
            icon = R.drawable.fieldicondescription,
            title = "Descrição",
            child = {
                Text(
                    text = "Esse talhão fica perto da cerca ao leste da fazenda, ao lado de outros talhões de banana prata.",
                    style = Typography.bodyLarge,
                    color = PRETO,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify
                )
            }
        )

        Information(
            space = if (taskList.isEmpty()) 60.dp else 40.dp,
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
                                    .padding(vertical = 18.dp, horizontal = 15.dp),
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
                                        .padding(vertical = 18.dp, horizontal = 15.dp),
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

        Information(
            space = 5.dp,
            icon = R.drawable.fieldicontroubleshoot,
            title = "Ultimas Análises",
            child = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = if (analysisList.isEmpty()) 60.dp else 40.dp,
                    )
                ) {
                    Text(
                        text = "Listadas abaixo estão as ultímas 5 análises feitas nesse talhão!",
                        style = Typography.bodyLarge,
                        color = VERDE_ESCURO,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Justify
                    )

                    when {
                        analysisList.isEmpty() -> {
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
                                    text = "Você ainda não\nrealizou nenhuma análise!",
                                    textAlign = TextAlign.Center,
                                    style = Typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = CINZA_ESCURO
                                )

                                Button(
                                    texto = "Nova Análise",
                                    modifier = Modifier
                                        .padding(vertical = 18.dp, horizontal = 15.dp),
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

                        else -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 10.dp
                                )
                            ) {
                                CreateDetails(
                                    username = "Gilmar",
                                    createdAt = "10 Maio 2024 ás 17:54"
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 60.dp
                                    ),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(
                                            space = 20.dp
                                        ),
                                    ) {
                                        analysisList.forEach { analysis ->
                                            ListItemCard(
                                                modifier = Modifier
                                                    .height(120.dp)
                                                    .widthIn(
                                                        max = 290.dp
                                                    ),
                                                onClick = {
                                                    navigationController.navigate("AnalysisInformation")
                                                },
                                                title = "Análise",
                                                titleStyle = Typography.labelSmall,
                                                nameStyle = Typography.titleLarge,
                                                name = analysis
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(end = 30.dp),
                                                    horizontalArrangement = Arrangement.spacedBy(
                                                        space = 10.dp,
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
                                                            style = Typography.displaySmall,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = BRANCO
                                                        )

                                                        Icon(
                                                            imageVector = ImageVector.vectorResource(
                                                                id = R.drawable.analysisiconliming
                                                            ),
                                                            contentDescription = "Icone de Calagem"
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
                                                            style = Typography.displaySmall,

                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = BRANCO
                                                        )

                                                        Icon(
                                                            imageVector = ImageVector.vectorResource(
                                                                id = R.drawable.analysisiconnpk
                                                            ),
                                                            contentDescription = "Icone de Npk"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }


                                    Button(
                                        texto = "Nova Análise",
                                        modifier = Modifier
                                            .padding(vertical = 18.dp, horizontal = 15.dp),
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
            }
        )
    }
}