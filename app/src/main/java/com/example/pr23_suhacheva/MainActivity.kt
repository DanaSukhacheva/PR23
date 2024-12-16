package com.example.pr23_suhacheva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pr23_suhacheva.ui.theme.Pr23_SuhachevaTheme
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pr23_SuhachevaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MedicApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MedicApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "launch_screen") {
        composable("launch_screen") { LaunchScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen() }
    }
}

@Composable
fun LaunchScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("onboarding") {
            popUpTo("launch_screen") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF50E3C2)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Смартлаб",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Логотип",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Composable
fun OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val titles = listOf("Анализы", "Уведомления", "Мониторинг")
    val descriptions = listOf(
        "Экспресс сбор и получение проб",
        "Вы быстро узнаете о результатах",
        "Наши врачи всегда наблюдают за вашими показателями здоровья"
    )
    val images = listOf(
        R.drawable.img_lab,
        R.drawable.img_doctor_patient,
        R.drawable.img_monitoring
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { page ->
            OnboardingPage(
                title = titles[page],
                description = descriptions[page],
                imageRes = images[page],
                isLastPage = page == 2,
                onSkip = { navController.navigate("login") },
                onNext = {
                    if (page < 2) {
                        LaunchedEffect(Unit) {
                            pagerState.animateScrollToPage(page + 1)
                        }
                    } else {
                        navController.navigate("login")
                    }
                }
            )
        }
    }
}

@Composable
fun OnboardingPage(
    title: String,
    description: String,
    imageRes: Int,
    isLastPage: Boolean,
    onSkip: () -> Unit,
    onNext: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(onClick = onSkip, modifier = Modifier.align(Alignment.Start)) {
            Text(if (isLastPage) "Завершить" else "Пропустить")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus_square),
                contentDescription = "Иконка квадрата с плюсом",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1DB954))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { onNext() }) {
                Text(if (isLastPage) "Начать" else "Далее")
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Экран входа и регистрации")
    }
}
