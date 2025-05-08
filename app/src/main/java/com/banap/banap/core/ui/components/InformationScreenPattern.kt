package com.banap.banap.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.core.ui.theme.BRANCO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InformationScreenPattern(
    navigationController: NavController,
    fixedRoute: String,
    title: String,
    titleIcon: Int,
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
                fixedRoute = fixedRoute
            )

            TitleInformation(
                title = title,
                icon = titleIcon
            )

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