package com.banap.banap.app.presentation.home.ui.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.shimmerEffect
import com.banap.banap.data.model.weather.WeatherResponse
import java.util.Calendar
import java.util.Locale

@Composable
fun Carousel(
    isLoading: Boolean,
    weather: WeatherResponse?,
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

    val pagerState = rememberPagerState(
        pageCount = {
            4
        }
    )

    Column(
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            pageSpacing = 30.dp
        ) { page ->
            Card(
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
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 35.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isLoading) {
                        Column {
                            Text(
                                text = if (weather != null) "${temperature.toInt()} °C" else "Erro",
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
                                text =
                                if (description.isEmpty())
                                    "Tente novamente"
                                else
                                    description.replaceFirstChar {
                                        if (it.isLowerCase())
                                            it.titlecase(Locale.ROOT)
                                        else
                                            it.toString()
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
                                    imageVector = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    contentDescription = "Erro no carregamento",
                                    modifier = Modifier.size(80.dp),
                                    tint = VERDE_CLARO
                                )
                            }

                            else -> {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    contentDescription = "Erro no carregamento",
                                    modifier = Modifier.size(60.dp),
                                    tint = VERDE_CLARO
                                )
                            }
                        }
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                space = 12.dp
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        shape = ShapeCarousel.medium
                                    )
                                    .width(80.dp)
                                    .height(25.dp)
                                    .shimmerEffect(),
                                content = {}
                            )

                            Box(
                                modifier = Modifier
                                    .clip(
                                        shape = ShapeCarousel.medium
                                    )
                                    .width(140.dp)
                                    .height(15.dp)
                                    .shimmerEffect(),
                                content = {}
                            )

                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(
                                            shape = ShapeCarousel.medium
                                        )
                                        .width(80.dp)
                                        .height(10.dp)
                                        .shimmerEffect(),
                                    content = {}
                                )
                            }
                        }

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_cloud_24),
                            contentDescription = "Erro no carregamento",
                            modifier = Modifier
                                .size(70.dp),
                            tint = Color(0xFFB8B5B5)
                        )
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) VERDE_CLARO else CINZA_CLARO
                val size = if (pagerState.currentPage == iteration) 16.dp else 8.dp

                Card(
                    modifier = Modifier
                        .clip(
                            shape = ShapeCarousel.small
                        )
                        .height(8.dp)
                        .width(size),
                    colors = CardDefaults.cardColors(
                        containerColor = color
                    )
                ) {
                }
            }
        }
    }
}
