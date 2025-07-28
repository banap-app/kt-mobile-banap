package com.banap.banap.app.presentation.analysis.ui.registration.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun ExplanationFormData(
    navigationController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(VERDE_CLARO)
                .height(350.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navigationController.popBackStack()
                },
                modifier = Modifier
                    .padding(
                        top = 40.dp,
                        start = 20.dp
                    )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                    contentDescription = "Icone de voltar",
                    tint = BRANCO,
                    modifier = Modifier
                        .scale(1.2F)
                )
            }

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisexplanationimage),
                contentDescription = "Imagem de explicação do formulário",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }

        Card(
            modifier = Modifier
                .padding(
                    top = 0.3 * screenHeight
                )
                .align(Alignment.BottomCenter)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = BRANCO,
                contentColor = VERDE_ESCURO
            ),
            shape = RoundedCornerShape(
                topStart = 45.dp,
                topEnd = 45.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 60.dp,
                        top = 30.dp
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 40.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 15.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Icone de pergunta",
                            modifier = Modifier
                                .scale(1.2F)
                        )

                        Text(
                            text = "O que é PRNT?",
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "Mede a eficácia do calcário em neutralizar a acidez do solo. Ajuda a determinar quão bem o calcário vai funcionar para corrigir a acidez do solo, garantindo que as plantas absorvam nutrientes adequadamente.",
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 15.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Icone de pergunta",
                            modifier = Modifier
                                .scale(1.2F)
                        )

                        Text(
                            text = "Como ele é utilizado?",
                            style = Typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = "A saturação por bases é um excelente indicativo das condições gerais de fertilidade do solo, sendo utilizada até como complemento na nomenclatura dos solos. Os solos podem ser divididos de acordo com a saturação por bases: solos eutróficos (férteis) = V% maior ou igual á 50%; solos distróficos (pouco férteis) = V% menor que 50%.",
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify
                    )
                }

                Card (
                    modifier = Modifier
                        .height(
                            max(
                                160.dp, 160.dp
                            )
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.analysisimagexplanation),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}