package com.banap.banap.core.ui.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InformationScreenPattern(
    navigationController: NavController,
    fieldId: String? = null,
    userName: String? = null,
    fixedRoute: String,
    title: String,
    titleIcon: Int,
    isLoadingDelete: Boolean = false,
    isLoading: Boolean,
    child: @Composable () -> Unit
) {
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
                        fieldId = fieldId,
                        userName = userName,
                        fixedRoute = fixedRoute
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
                                title = title,
                                icon = titleIcon
                            )
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(40.dp))
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 60.dp
                        )
                    ) {
                        child()
                    }
                }
            }
        } else {
            LoadingScreen()
        }
    }
}