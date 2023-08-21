package com.example.swordssocks.game_components

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
import com.example.swordssocks.R
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