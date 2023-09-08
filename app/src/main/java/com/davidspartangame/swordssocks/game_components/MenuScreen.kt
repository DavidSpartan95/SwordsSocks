package com.davidspartangame.swordssocks.game_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.davidspartangame.swordssocks.R
import com.davidspartangame.swordssocks.database.UserRepository
import com.davidspartangame.swordssocks.nav_graph.Screen
import com.davidspartangame.swordssocks.ui.theme.DarkOrange
import com.davidspartangame.swordssocks.ui.theme.DarkRed
import com.davidspartangame.swordssocks.ui.theme.SandPaper
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MenuScreen(
    navController: NavHostController,
    userRepository: UserRepository
) {
    val systemUiController: SystemUiController = rememberSystemUiController()

    systemUiController.isStatusBarVisible = false // Status bar
    systemUiController.isNavigationBarVisible = false // Navigation bar
    systemUiController.isSystemBarsVisible = false // Status & Navigation bars
    Box(
        Modifier.fillMaxWidth().background(Color.Black),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.introscreen_text),
            contentDescription = "",
            Modifier.fillMaxSize().padding(bottom = 50.dp)
        )

        Box(Modifier.align(Alignment.BottomCenter)) {
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = {
                        navController.navigate(route = "creation_screen"){
                            popUpTo(Screen.Home.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                ) {
                    Text(
                        text = "New Game",
                        color = Color.White,
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(route = "load_screen"){
                            popUpTo(Screen.Home.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                ) {
                    Text(
                        text = "Load Game",
                        color = Color.White,
                    )
                }
            }
        }
    }
}