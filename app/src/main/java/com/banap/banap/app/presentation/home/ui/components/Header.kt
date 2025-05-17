package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.setColorInText
import com.banap.banap.data.model.menu.DropDownItem
import com.banap.banap.data.model.menu.MenuOption

@Composable
fun Header(
    nome: String,
    onItemClick: (DropDownItem) -> Unit,
    navigationController: NavController
) {
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
                textoASerDestacado = "$nome!",
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
                            navigationController.navigate("EditProfile")
                        }
                    )
                )
            )
        }
    }
}