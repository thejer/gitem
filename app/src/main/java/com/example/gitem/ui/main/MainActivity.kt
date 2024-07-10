package com.example.gitem.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gitem.ui.theme.GitemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            val paddingValues = PaddingValues(bottom = innerPadding.calculateBottomPadding())
            NavigationHost(
                modifier = Modifier
                    .background(Color.White)
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
