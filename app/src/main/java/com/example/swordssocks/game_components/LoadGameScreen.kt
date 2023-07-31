package com.example.swordssocks.game_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.database.retrieveAllUsers
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.DarkOrange
import com.google.gson.Gson
import kotlinx.coroutines.withContext

@Composable
fun LoadGameScreen(
    navController: NavHostController,
    userRepository: UserRepository,
)
{
    var saveFiles: List<User>? by remember { mutableStateOf(null) }
    LaunchedEffect(true){
        saveFiles = retrieveAllUsers(userRepository)
    }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
            navController.navigate(route = "home_screen"){
                popUpTo(Screen.Load.route){
                    inclusive = true
                }}

        },colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
        ) {
            Text("Go Back", color = Color.White)
        }
        saveFiles?.let{
            for (x in saveFiles!!){
                Button(onClick = {
                    val userJson = Gson().toJson(x)
                    navController.navigate(route = "town_screen/$userJson"){
                        popUpTo(Screen.Load.route){
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "${x.name} level: ${x.level}")
                }
            }
            if (saveFiles!!.isEmpty()){
                Box(
                    modifier = Modifier
                    .size(height = 90.dp, width = 290.dp)
                )
                {

                    Text("EMPTY",
                        Modifier.align(Alignment.Center),
                        fontSize = 50.sp, color = Color.White
                    )
                }
            }
        }
    }
}