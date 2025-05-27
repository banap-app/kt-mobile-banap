package com.banap.banap.app.presentation.property.ui.listing.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Container(
    navigationController: NavController,
    titulo: String,
    children: @Composable () -> Unit,
    buttonValue: String,
    isLoading: Boolean,
    propertyList: MutableList<String>
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO,
        floatingActionButton = {
            if (propertyList.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(
                            end = 14.dp
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        texto = buttonValue,
                        modifier = Modifier
                            .padding(vertical = 18.dp, horizontal = 15.dp),
                        hasIcon = true,
                        shape = ShapeProperty.small,
                        onClick = {
                            navigationController.navigate("NewField")
                        },
                        backgroundColor = BRANCO,
                        contentColor = VERDE_CLARO,
                        defaultElevetion = 3.dp
                    )
                }
            }

        }
    ) {
        Column(
            modifier =
            if (isLoading) {
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 60.dp)
                    .fillMaxSize()
            } else {
                Modifier
                    .fillMaxSize()
            }
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fallbackRoute = "Home"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Icone de propriedade",
                    tint = VERDE_ESCURO,
                    modifier = Modifier
                        .scale(1.2f)
                )

                Text(
                    text = titulo,
                    style = Typography.titleLarge,
                    color = VERDE_ESCURO
                )
            }

            if (propertyList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(60.dp))
            }

            Column {
                children()
            }
        }
    }
}