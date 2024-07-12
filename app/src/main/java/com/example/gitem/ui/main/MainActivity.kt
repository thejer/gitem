package com.example.gitem.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gitem.ui.nav.BottomNavigationBar
import com.example.gitem.ui.nav.NavigationHost
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<SplashViewModel>()
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isSplashShow.value
        }
        setContent {
            val navController = rememberNavController()
            GitemTheme {
                MainPage(navController)
            }
        }
    }

    @Composable
    private fun MainPage(navController: NavHostController) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }) { innerPadding ->
            val paddingValues = PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
            )
            NavigationHost(
                modifier = Modifier
                    .background(White)
                    .padding(paddingValues = paddingValues),
                navController = navController
            )
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        val navController = rememberNavController()
        GitemTheme {
            MainPage(navController)
        }
    }
}
