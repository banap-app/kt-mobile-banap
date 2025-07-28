package com.banap.banap.app.presentation.producer.ui.screens.userchoice.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.banap.banap.core.ui.theme.PRETO

fun setColorInTextUserChoice (
    primeiroTexto: String,
    primeiroTextoASerDestacado: String,
    corPrimeiroTexto: Color,
    segundoTexto: String,
    segundoTextoASerDestacado: String,
    corSegundoTexto: Color
) : AnnotatedString {
    var formattedString: AnnotatedString? = null

    formattedString = buildAnnotatedString {
        append(primeiroTexto)

        pushStyle(
            SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = corPrimeiroTexto
            )
        )

        append(primeiroTextoASerDestacado)

        pushStyle(
            SpanStyle(
                fontWeight = FontWeight.Light,
                color = PRETO
            )
        )

        append(segundoTexto)

        pushStyle(
            SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = corSegundoTexto
            )
        )

        append(segundoTextoASerDestacado)

        toAnnotatedString()
    }

    return formattedString
}