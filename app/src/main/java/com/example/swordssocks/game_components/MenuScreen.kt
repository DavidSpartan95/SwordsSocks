package com.example.swordssocks.game_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.DarkOrange
import com.example.swordssocks.ui.theme.DarkRed
import com.example.swordssocks.ui.theme.SandPaper

@Composable
fun MenuScreen(
    navController: NavHostController,
    userRepository: UserRepository
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Swords and Socks",
            color = DarkRed,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
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