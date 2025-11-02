package com.banap.banap.app.presentation.home.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.skeleton.ui.home.components.HeaderHomeSkeleton
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.getFirstName
import com.banap.banap.core.ui.util.setColorInText
import com.banap.banap.data.model.menu.DropDownItem
import com.banap.banap.data.model.menu.MenuOption
import com.banap.banap.domain.model.producer.ProducerState

@Composable
fun Header(
    name: String,
    navigationController: NavController,
    getProducerByIdState: ProducerState,
    onItemClick: (DropDownItem) -> Unit
) {
    var error: String by remember {
        mutableStateOf("")
    }

    var loading: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(getProducerByIdState.error) {
        getProducerByIdState.error?.let {
            Log.d("ERROR", it)
            error = it
        }
    }

    LaunchedEffect(getProducerByIdState.isLoading) {
        getProducerByIdState.isLoading?.let {
            Log.d("LOADING", it.toString())
            loading = it
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when {
            name.isNotEmpty() -> {
                Text(
                    text = setColorInText(
                        texto = "Olá, ",
                        textoASerDestacado = "${getFirstName(name)}!",
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
                                optionSelected = onItemClick
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

            error.isNotEmpty() -> {
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
                        error = error
                    )

                    Menu(
                        dropDownItems = listOf(
                            DropDownItem(
                                option = MenuOption(
                                    icon = R.drawable.baseline_logout_24,
                                    text = "Sair"
                                ),
                                optionSelected = onItemClick
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

            else -> {
                HeaderHomeSkeleton()
            }
        }
    }
}