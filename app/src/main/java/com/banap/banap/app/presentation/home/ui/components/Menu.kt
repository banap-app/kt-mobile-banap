package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.data.model.menu.DropDownItem

@Composable
fun Menu(
    dropDownItems: List<DropDownItem>
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffSet by remember {
        mutableStateOf(DpOffset.Zero)
    }

    Image(
        painter = painterResource(R.drawable.user_error),
        contentDescription = "Foto de perfil",
        modifier = Modifier
            .size(45.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onTap = {
                        isContextMenuVisible = true
                        pressOffSet = DpOffset(it.x.toDp(), it.y.toDp())
                    }
                )
            }
    )

    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = {
            isContextMenuVisible = false
        },
        modifier = Modifier
            .background(BRANCO),
        shape = RoundedCornerShape(10.dp),
        offset = DpOffset(x = 0.dp, y = 10.dp)
    ) {
        dropDownItems.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    item.optionSelected(item)
                    isContextMenuVisible = false
                },
                text = {
                    Text(
                        text = item.option.text,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = PRETO
                    )
                },
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp
                    )
                    .background(BRANCO),
                leadingIcon = {
                    Image(
                        imageVector = ImageVector.vectorResource(id = item.option.icon),
                        contentDescription = "Icone de logout",
                        colorFilter = ColorFilter.tint(PRETO)
                    )
                }
            )
        }
    }
}