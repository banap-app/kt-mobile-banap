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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    fixedRoute: String,
    title: String,
    titleIcon: Int,
    isLoading: Boolean,
    child: @Composable () -> Unit
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
                fieldId = fieldId,
                fixedRoute = fixedRoute
            )

            when {
                isLoading -> {
                    Row (
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

                else -> {
                    TitleInformation(
                        title = title,
                        icon = titleIcon
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

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
}