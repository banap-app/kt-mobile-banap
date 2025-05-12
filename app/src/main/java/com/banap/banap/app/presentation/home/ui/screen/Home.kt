package com.banap.banap.app.presentation.home.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.home.ui.components.Carousel
import com.banap.banap.app.presentation.home.ui.components.Header
import com.banap.banap.app.presentation.home.ui.components.Property
import com.banap.banap.app.presentation.home.ui.components.RecentActivities
import com.banap.banap.app.presentation.home.ui.components.Tasks
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.home.HomeSkeleton
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.domain.viewmodel.TokenVerificationViewModel
import kotlinx.coroutines.delay

@Composable
fun Home(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    tokenVerificationViewModel: TokenVerificationViewModel
) {
    val context = LocalContext.current

    val tokenVerificationState = tokenVerificationViewModel.state.value

    var isTokenValid: Boolean by remember {
        mutableStateOf(false)
    }

    var hasToken: String? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(context) {
        Log.d("TOKEN", tokenViewModel.getToken("token").toString())

        tokenViewModel.getToken("token")?.let { token ->
            hasToken = token
            tokenVerificationViewModel.verifyToken(token)
        } ?:
        run {
            Log.d("NO_TOKEN", "Voce nao possui um token de autenticação e será redirecionado para tela de login")
            delay(1_000)
            tokenViewModel.clearAll()
            navigationController.navigate("Login")
        }
    }

    LaunchedEffect(tokenVerificationState.response) {
        Log.d("RESPONSE", tokenVerificationState.response?.decodedToken ?: "sem resposta")

        tokenVerificationState.response?.let {
            tokenViewModel.saveToken("verifiedToken", it.success.toString())
            isTokenValid = it.success
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        Log.d("ERROR", tokenVerificationState.error)

        if (tokenVerificationState.error.isNotEmpty()) {
            tokenViewModel.clearAll()
            navigationController.navigate("Login")
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) { innerPadding ->
        if (isTokenValid && !hasToken.isNullOrEmpty()) {
            Column (
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 60.dp),
            ) {
                Header(
                    nome = "Gilmar",
                    navigationController = navigationController,
                    onItemClick = {
                        tokenViewModel.clearAll()
                        navigationController.navigate("Login")
                    }
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas),
                    contentDescription = "Linhas que separam o conteúdo",
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            bottom = 40.dp
                        )
                )

                Carousel()

                Spacer(modifier = Modifier.height(60.dp))

                Property(
                    titulo = "Propriedade 01",
                    navigationController = navigationController
                )

                Spacer(modifier = Modifier.height(60.dp))

                RecentActivities(
                    titulo = "Atividades recentes",
                    atividadeRealizada = "cadastrou uma propriedade.",
                    autorAtividade = "Você "
                )

                Spacer(modifier = Modifier.height(60.dp))

                Tasks(
                    titulo = "Lista de tarefas",
                    subTitulo = "Seus afazeres da semana!",
                    navigationController
                )
            }
        } else {
            HomeSkeleton(
                padding = innerPadding.calculateTopPadding()
            )
        }
    }
}