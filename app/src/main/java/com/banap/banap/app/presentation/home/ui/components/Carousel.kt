package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import java.util.Calendar
import java.util.Locale

@Composable
fun Carousel(
    temperature: Double,
    description: String,
    iconUrl: String
) {
    val currentTime = Calendar.getInstance()
    val dayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK)
    val hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    val hourString = String.format("%02d", hourOfDay)
    val minuteString = String.format("%02d", minute)

    val painter = rememberAsyncImagePainter(model = iconUrl)
    val state by painter.state.collectAsState()

    val dayOfWeekString = when (dayOfWeek) {
        Calendar.SUNDAY -> "Domingo"
        Calendar.MONDAY -> "Segunda-feira"
        Calendar.TUESDAY -> "Terça-feira"
        Calendar.WEDNESDAY -> "Quarta-feira"
        Calendar.THURSDAY -> "Quinta-feira"
        Calendar.FRIDAY -> "Sexta-feira"
        Calendar.SATURDAY -> "Sábado"
        else -> ""
    }

    Column (
        modifier = Modifier
            .padding(horizontal = 30.dp)
    ) {
        Card (
            modifier = Modifier
                .shadow(elevation = 3.dp, shape = ShapeCarousel.medium)
                .clip(
                    shape = ShapeCarousel.medium
                )
                .height(156.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = BRANCO
            )
        ) { // se estiver carregando, trocar por um skeleton
            Row (
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${temperature.toInt()} °C",
                        style = Typography.headlineLarge,
                        color = VERDE_CLARO
                    )
                    Text(
                        text = "$dayOfWeekString, $hourString:$minuteString",
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        color = VERDE_CLARO
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = description.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        },
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Light,
                        color = VERDE_CLARO
                    )
                }

                when (state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(40.dp),
                            strokeWidth = 4.dp,
                            color = VERDE_CLARO
                        )
                    }

                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = "Ícone do clima",
                            modifier = Modifier
                                .size(100.dp)
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Erro no carregamento",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    else -> {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Erro no carregamento",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Row (
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Card (
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.small
                    )
                    .height(8.dp)
                    .width(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = VERDE_CLARO
                )
            ) {
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Card (
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.large
                    )
                    .size(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CINZA_CLARO
                )
            ) {
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Card (
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.large
                    )
                    .size(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CINZA_CLARO
                )
            ) {
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Card (
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.large
                    )
                    .size(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CINZA_CLARO
                )
            ) {
            }
        }
    }
}
