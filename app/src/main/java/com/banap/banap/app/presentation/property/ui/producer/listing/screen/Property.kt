package com.banap.banap.app.presentation.property.ui.producer.listing.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.property.ui.producer.listing.components.Container
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.shimmerEffect
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.data.model.menu.DropDownItem
import com.banap.banap.data.model.menu.MenuOption
import com.banap.banap.domain.viewmodel.field.ListFieldsViewModel
import com.banap.banap.domain.viewmodel.property.DeletePropertyViewModel

@Composable
fun Property(
    navigationController: NavController,
    name: String,
    userName: String,
    propertyId: String,
    producerId: String,
    listFieldsViewModel: ListFieldsViewModel,
    tokenViewModel: TokenViewModel,
    deletePropertyViewModel: DeletePropertyViewModel = hiltViewModel()
) {
    val deletePropertyState = deletePropertyViewModel.state.value
    val listFieldsState by listFieldsViewModel.state

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    var deleteIsLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var deleteError by remember {
        mutableStateOf("")
    }

    var fields: List<FieldResponse> by remember {
        mutableStateOf(listOf())
    }

    var modalVisible: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        tokenViewModel.clearToken("propertyName")
    }

    LaunchedEffect(true) {
        if (!userName.contains("userName")) {
            tokenViewModel.saveToken("userName", userName)
            Log.d("USERNAME", userName)
        }
    }

    LaunchedEffect(true) {
        if (!name.contains("name")) {
            tokenViewModel.saveToken("listingPropertyName", name)
            Log.d("PROPERTYNAME", name)
        }
    }

    LaunchedEffect(true) {
        if (!producerId.contains("producerId")) {
            tokenViewModel.saveToken("producerId", producerId)
        }
    }

    LaunchedEffect(true) {
        if (tokenViewModel.getToken("propertyId").isNullOrEmpty()) {
            listFieldsViewModel.listFields(propertyId)
            Log.d("ID_VINDO", propertyId)
        } else {
            listFieldsViewModel.listFields(tokenViewModel.getToken("propertyId") ?: "")
            Log.d("ID_TOKEN", tokenViewModel.getToken("propertyId") ?: "")
        }
    }

    LaunchedEffect(listFieldsState.isLoading) {
        isLoading = listFieldsState.isLoading
    }

    LaunchedEffect(listFieldsState.response) {
        listFieldsState.response.let {
            it.forEach { (id, listFields) ->
                fields = listFields
                tokenViewModel.saveToken("propertyId", id)
                Log.d("ID_SALVO", tokenViewModel.getToken("propertyId") ?: "")
            }
        }
    }

    LaunchedEffect(listFieldsState.error) {
        error = listFieldsState.error
    }

    LaunchedEffect(deletePropertyState.response) {
        deletePropertyState.response?.let {
            if (it.success) {
                navigationController.navigate("Home")
            }
        }
    }

    LaunchedEffect(deletePropertyState.isLoading) {
        deleteIsLoading = deletePropertyState.isLoading
    }

    LaunchedEffect(deletePropertyState.error) {
        deleteError = deletePropertyState.error
    }

    Container(
        navigationController = navigationController,
        titulo = tokenViewModel.getToken("listingPropertyName") ?: name,
        children = {
            when {
                isLoading -> {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 30.dp,
                                end = 30.dp,
                                top = 60.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .fillMaxWidth()
                                .height(150.dp)
                                .shimmerEffect(),
                            content = {}
                        )

                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .fillMaxWidth()
                                .height(150.dp)
                                .shimmerEffect(),
                            content = {}
                        )

                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .fillMaxWidth()
                                .height(150.dp)
                                .shimmerEffect(),
                            content = {}
                        )

                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .fillMaxWidth()
                                .height(150.dp)
                                .shimmerEffect(),
                            content = {}
                        )

                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .fillMaxWidth()
                                .height(150.dp)
                                .shimmerEffect(),
                            content = {}
                        )
                    }
                }

                fields.isEmpty() -> {
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
                            text = "Ainda não há um\ntalhão cadastrado!",
                            textAlign = TextAlign.Center,
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = CINZA_ESCURO
                        )

                        Button(
                            texto = "Novo Talhão",
                            modifier = Modifier
                                .padding(vertical = 18.dp, horizontal = 15.dp),
                            hasIcon = true,
                            shape = ShapeProperty.small,
                            onClick = {
                                navigationController.navigate(
                                    Screen.NewField.createRoute(
                                        producerId = tokenViewModel.getToken("producerId")
                                            ?: producerId,
                                        propertyId = tokenViewModel.getToken("propertyId")
                                            ?: propertyId,
                                        fieldId = "fieldId"
                                    )
                                )
                            },
                            backgroundColor = VERDE_CLARO,
                            contentColor = BRANCO,
                            defaultElevetion = 3.dp
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = 30.dp,
                                end = 30.dp
                            )
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(fields.size) {
                            ListItemCard(
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    navigationController.navigate(
                                        Screen.Information.createRoute(
                                            fieldId = fields[it].id,
                                            userName = tokenViewModel.getToken("userName")
                                        )
                                    )
                                },
                                title = "Talhão",
                                titleStyle = Typography.titleMedium,
                                nameStyle = Typography.displayLarge,
                                name = fields[it].name
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.propertyimagefield),
                                    contentDescription = "Imagem do Talhao da Propriedade"
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }
                }
            }
        },
        buttonValue = "Novo Talhão",
        onClick = {
            navigationController.navigate(
                Screen.NewField.createRoute(
                    producerId = tokenViewModel.getToken("producerId") ?: producerId,
                    propertyId = tokenViewModel.getToken("propertyId") ?: propertyId,
                    fieldId = "fieldId"
                )
            )
        },
        isLoading = isLoading,
        deleteIsLoading = deleteIsLoading,
        modalVisible = modalVisible,
        dropDownItems = listOf(
            DropDownItem(
                option = MenuOption(
                    icon = R.drawable.fieldicondelete,
                    text = "Deletar"
                ),
                optionSelected = {
                    Log.d("ID_DELETE_TOKEN", tokenViewModel.getToken("propertyId") ?: "")
                    Log.d("ID_DELETE_VINDO", propertyId)

                    modalVisible = true
                }
            ),
            DropDownItem(
                option = MenuOption(
                    icon = R.drawable.fieldiconedit,
                    text = "Editar"
                ),
                optionSelected = {
                    navigationController.navigate(
                        Screen.NewProperty.createRoute(
                            propertyId = tokenViewModel.getToken("propertyId") ?: propertyId
                        )
                    )
                }
            )
        ),
        modalOnConfirm = {
            Log.d("ID_DELETE_TOKEN", tokenViewModel.getToken("propertyId") ?: "")
            Log.d("ID_DELETE_VINDO", propertyId)

            deletePropertyViewModel.deleteProperty(
                tokenViewModel.getToken("propertyId") ?: propertyId
            )

            modalVisible = false
        },
        modalOnDismiss = {
            modalVisible = false
        },
        propertyList = fields
    )
}
