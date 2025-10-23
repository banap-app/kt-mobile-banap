package com.banap.banap.app.presentation.property.ui.producer.listing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.data.model.menu.DropDownItem

@Composable
fun Container(
    navigationController: NavController,
    titulo: String,
    children: @Composable () -> Unit,
    buttonValue: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    deleteIsLoading: Boolean,
    modalVisible: Boolean,
    dropDownItems: List<DropDownItem>,
    modalOnConfirm: () -> Unit,
    modalOnDismiss: () -> Unit,
    propertyList: List<FieldResponse>
) {
    val density = LocalDensity.current

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffSet by remember {
        mutableStateOf(DpOffset.Zero)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO,
        floatingActionButton = {
            if (!isLoading && !deleteIsLoading) {
                Row(
                    modifier = Modifier
                        .padding(
                            start = 54.dp,
                            end = 14.dp
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        texto = "",
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val position = coordinates.localToWindow(Offset.Zero)
                                pressOffSet = with(density) {
                                    DpOffset(
                                        position.x.toDp(),
                                        position.y.toDp() - 10.dp
                                    )
                                }

                            }
                            .padding(15.dp),
                        hasIcon = true,
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_menu_24),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            isContextMenuVisible = true
                        },
                        backgroundColor = BRANCO,
                        contentColor = VERDE_CLARO,
                        defaultElevetion = 3.dp
                    )

                    if (propertyList.isNotEmpty()) {
                        Button(
                            texto = buttonValue,
                            modifier = Modifier
                                .padding(vertical = 18.dp, horizontal = 15.dp),
                            hasIcon = true,
                            shape = ShapeProperty.small,
                            onClick = onClick,
                            backgroundColor = BRANCO,
                            contentColor = VERDE_CLARO,
                            defaultElevetion = 3.dp
                        )
                    }
                }
            }

            if (modalVisible) {
                Modal(
                    onConfirm = modalOnConfirm,
                    onDismiss = modalOnDismiss,
                    icon = ImageVector.vectorResource(id = R.drawable.fieldicondelete),
                    iconColor = VERMELHO,
                    title = "Tem certeza que deseja\n apagar a propriedade?",
                    description = "Apagando a propriedade, todas as informações relacionadas a ele tambem serão apagadas!",
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
    ) { innerPadding ->
        if (!deleteIsLoading) {
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = {
                    isContextMenuVisible = false
                },
                modifier = Modifier
                    .background(BRANCO),
                shape = RoundedCornerShape(10.dp),
                offset = pressOffSet
            ) {
                dropDownItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            item.optionSelected(item)
                            isContextMenuVisible = false
                        },
                        text = {
                            Text(
                                text = item.option.text,
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = PRETO
                            )
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp
                            )
                            .background(BRANCO),
                        leadingIcon = {
                            Image(
                                imageVector = ImageVector.vectorResource(id = item.option.icon),
                                contentDescription = "Icone de logout",
                                colorFilter = ColorFilter.tint(PRETO),
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    )
                }
            }

            Column(
                modifier =
                if (isLoading) {
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = 60.dp
                        )
                        .fillMaxSize()
                } else {
                    Modifier
                        .padding(
                            top = innerPadding.calculateTopPadding()
                        )
                        .fillMaxSize()
                }
            ) {
                RegistrationHeader(
                    navigationController = navigationController,
                    fixedRoute = "Home"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 15.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Icone de propriedade",
                        tint = VERDE_ESCURO,
                        modifier = Modifier
                            .scale(1.5f)
                    )

                    Text(
                        text = titulo,
                        style = Typography.titleLarge,
                        color = VERDE_ESCURO,
                        textAlign = TextAlign.Center
                    )
                }

                if (propertyList.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(60.dp))
                }

                Column {
                    children()
                }
            }
        } else {
            LoadingScreen()
        }
    }
}