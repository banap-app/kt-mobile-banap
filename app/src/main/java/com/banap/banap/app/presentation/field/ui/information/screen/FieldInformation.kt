package com.banap.banap.app.presentation.field.ui.information.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.field.ui.information.components.FieldActions
import com.banap.banap.app.presentation.field.ui.information.components.FieldImage
import com.banap.banap.app.presentation.field.ui.information.components.FieldTitle
import com.banap.banap.app.presentation.field.ui.information.components.Information
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FieldInformation(
    navigationController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fixedRoute = "Home"
            )

            FieldTitle(
                title = "Talhão 01"
            )

            Spacer(Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 60.dp
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    FieldImage(
                        cultivation = "Banana Nanica",
                        numberCultivation = "10",
                        image = R.drawable.fieldimage
                    )

                    FieldActions(
                        navigationController
                    )
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
                    space = 60.dp,
                    icon = R.drawable.fieldicontask,
                    title = "Lista de Tarefas",
                    child = {
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
                                space = 60.dp,
                            )
                        ) {
                            Text(
                                text = "Listadas abaixo estão as ultímas 5 análises feitas nesse talhão!",
                                style = Typography.bodyLarge,
                                color = VERDE_ESCURO,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.Justify
                            )

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
                                        navigationController.navigate("NewAnalysis")
                                    },
                                    backgroundColor = VERDE_CLARO,
                                    contentColor = BRANCO,
                                    defaultElevetion = 3.dp
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}