package com.banap.banap.app.presentation.readytostart.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.ShapeLogin
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReadyToStart(
    navigationController: NavController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 100.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.gardener),
                        contentDescription = "Imagem de uma mulher com uma planta na mão"
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Text(
                        text = "Pronto para começar?",
                        style = Typography.titleLarge,
                        fontSize = 22.sp,
                        color = VERDE_ESCURO
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Este pequeno ‘tutorial’ é apenas uma curta demonstração do que nosso sistema tem a oferecer. Ainda há muito mais por vir!",
                        textAlign = TextAlign.Center,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = VERDE_ESCURO
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 30.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { iteration ->
                            val color = if (iteration == 4) VERDE_ESCURO else CINZA_CLARO

                            Card(
                                modifier = Modifier
                                    .size(10.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = color
                                )
                            ) {

                            }
                        }
                    }
                }

//                Image(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas_ready_to_start),
//                    contentDescription = "Linhas da tela",
//                    modifier = Modifier
//                        .absoluteOffset(
//                            y = 220.dp
//                        )
//                        .scale(1.1F)
//                )
            }


            Column (
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button (
                    texto = "Cadastrar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 11.dp),
                    hasIcon = false,
                    shape = ShapeLogin.small,
                    onClick = {
                        navigationController.navigate("UserChoice")
                    },
                    backgroundColor = VERDE_CLARO,
                    contentColor = BRANCO,
                    defaultElevetion = 1.dp
                )

                Button (
                    texto = "Logar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 11.dp),
                    hasIcon = false,
                    shape = ShapeLogin.small,
                    onClick = {
                        navigationController.navigate("Login")
                    },
                    backgroundColor = BRANCO,
                    contentColor = VERDE_CLARO,
                    defaultElevetion = 1.dp
                )
            }

        }
    }
}