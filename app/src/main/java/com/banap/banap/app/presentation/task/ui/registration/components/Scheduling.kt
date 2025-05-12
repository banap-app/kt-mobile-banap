package com.banap.banap.app.presentation.task.ui.registration.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.banap.banap.app.presentation.task.ui.registration.enum.PickerTarget
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERMELHO
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scheduling(
    label: String,
    startTime: MutableState<String>,
    endTime: MutableState<String>,
    startError: MutableState<String>,
    endError: MutableState<String>
) {
    var currentPicker by remember {
        mutableStateOf<PickerTarget?>(null)
    }

    val formatter = remember {
        DateTimeFormatter.ofPattern("HH:mm")
    }

    currentPicker?.let { target ->
        ShowTimePickerDialog(
            onConfirm = { state ->
                val formatted = LocalTime.of(state.hour, state.minute).format(formatter)

                when (target) {
                    PickerTarget.START -> {
                        startError.value = ""
                        startTime.value = formatted
                    }

                    PickerTarget.END -> {
                        endError.value = ""
                        endTime.value = formatted
                    }
                }

                currentPicker = null
            },
            onDismiss = {
                currentPicker = null
            }
        )
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = label,
                style = Typography.titleMedium,
                color = PRETO
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = "Começar ás",
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = PRETO
                        )

                        Card(
                            onClick = {
                                currentPicker = PickerTarget.START
                            },
                            modifier = Modifier
                                .width(
                                    min(
                                        90.dp,
                                        90.dp
                                    )
                                ),
                            shape = RoundedCornerShape(7.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (startError.value.isEmpty()) VERDE_CLARO else VERMELHO,
                                contentColor = BRANCO
                            )
                        ) {
                            Text(
                                text = startTime.value.ifEmpty { "00 : 00" },
                                style = Typography.bodySmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                color = BRANCO,
                                modifier = Modifier
                                    .padding(
                                        vertical = 13.dp
                                    )
                                    .fillMaxSize()
                            )
                        }
                    }

                    if (startError.value.isNotEmpty()) {
                        Text(
                            text = startError.value,
                            color = VERMELHO,
                            style = Typography.displaySmall
                        )
                    }
                }


                Text(
                    text = "até as",
                    style = Typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    color = PRETO
                )

                Column {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Terminar ás",
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = PRETO
                        )

                        Card(
                            onClick = {
                                currentPicker = PickerTarget.END
                            },
                            modifier = Modifier
                                .width(
                                    min(
                                        90.dp,
                                        90.dp
                                    )
                                ),
                            shape = RoundedCornerShape(7.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (endError.value.isEmpty()) VERDE_CLARO else VERMELHO,
                                contentColor = BRANCO
                            )
                        ) {
                            Text(
                                text = endTime.value.ifEmpty { "00 : 00" },
                                style = Typography.bodySmall,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                color = BRANCO,
                                modifier = Modifier
                                    .padding(
                                        vertical = 13.dp
                                    )
                                    .fillMaxSize()
                            )
                        }
                    }

                    if (endError.value.isNotEmpty()) {
                        Text(
                            text = endError.value,
                            color = VERMELHO,
                            style = Typography.displaySmall
                        )
                    }
                }

            }
        }
    }
}