package com.davidspartangame.swordssocks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.davidspartangame.swordssocks.database.*
import com.davidspartangame.swordssocks.game_components.CreationScreen
import com.davidspartangame.swordssocks.game_components.MenuScreen
import com.davidspartangame.swordssocks.gladiator_items.Potion
import com.davidspartangame.swordssocks.gladiator_items.smallPotion
import com.davidspartangame.swordssocks.nav_graph.SetupNavGraph

import com.davidspartangame.swordssocks.ui.theme.SandColor
import com.davidspartangame.swordssocks.ui.theme.SwordsSocksTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val db = AppDatabase.getInstance(applicationContext)
        val userRepository = UserRepository(db, lifecycleScope)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContent {

            SwordsSocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = SandColor
                ) {
                    val context = LocalContext.current
                    
                    navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        userRepository = userRepository
                    )
                }
            }
        }
    }
}



