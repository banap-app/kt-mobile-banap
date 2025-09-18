package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.Typography

@Composable
fun Notifications(
    error: String? = null
) {
    var isNotificationsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            isNotificationsVisible = !isNotificationsVisible
        },
        modifier = Modifier
            .padding(end = 10.dp)
    ) {
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = "Icone de notificação",
            modifier = Modifier
                .scale(1.0F)
        )
    }

    DropdownMenu(
        expanded = isNotificationsVisible,
        onDismissRequest = {
            isNotificationsVisible = false
        },
        modifier = Modifier
            .background(BRANCO),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            color = CINZA_INTERMEDIARIO,
            width = 0.5.dp
        )
    ) {
        Text(
            text = if (error.isNullOrEmpty()) "Não há novas mensagens!" else "Ocorreu um erro...",
            style = Typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            color = CINZA_INTERMEDIARIO,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}