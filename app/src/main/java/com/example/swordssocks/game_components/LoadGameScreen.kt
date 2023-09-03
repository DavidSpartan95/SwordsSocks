package com.example.swordssocks.game_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.database.retrieveAllUsers
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.DarkGreen
import com.example.swordssocks.ui.theme.DarkOrange
import com.example.swordssocks.ui.theme.SandColor
import com.example.swordssocks.ui.theme.SandPaper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

@Composable
fun LoadGameScreen(
    navController: NavHostController,
    userRepository: UserRepository,
)
{
    var saveFiles: List<User>? by remember { mutableStateOf(null) }
    var userToDelete: User? by remember { mutableStateOf(null) }

    var popUp by remember { mutableStateOf(false) }

    LaunchedEffect(true, popUp){

        saveFiles = retrieveAllUsers(userRepository)
    }
    if (popUp){
        ConfirmationPopUp(no = { popUp = false }) {
            userRepository.performDatabaseOperation(Dispatchers.IO){
                userRepository.deleteUser(userToDelete!!)
                CoroutineScope(Dispatchers.Main).launch {
                    popUp = false
                }
            }
        }
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ){
                items(saveFiles!!.size) { index ->
                    Column(
                        modifier = Modifier
                            .background(SandColor)
                            .fillMaxWidth()
                            .padding(2.dp)
                            .border(
                                width = 5.dp,
                                color = DarkOrange,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    userToDelete = saveFiles!![index]
                                    popUp = true
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text(text = "DELETE", color = Color.White)
                            }

                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                            ) {
                                Text(
                                    text = "${saveFiles!![index].name} level: ${saveFiles!![index].level}",
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            }
                            Button(
                                onClick = {
                                    val userJson = Gson().toJson(saveFiles!![index])
                                    navController.navigate(route = "town_screen/$userJson") {
                                        popUpTo(Screen.Load.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
                            ) {
                                Text(text = "LOAD", color = Color.White)
                            }
                        }
                    }
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

@Composable
fun ConfirmationPopUp(no:()-> Unit,yes:()-> Unit,) {
    Popup() {
        Box(
            Modifier
                .fillMaxSize()
                .background(SandColor), contentAlignment = Alignment.Center) {

            Box(
                Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(300.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,
            ){
                Column(
                    Modifier.fillMaxHeight(),
                    Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    Text(
                        text = "Delete Character?", fontWeight = FontWeight.Bold, fontSize = 30.sp
                    )

                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                        Button(onClick = {
                            yes.invoke()
                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                        ) {
                            Text(text = "DELETE", color = Color.White)
                        }
                        Button(onClick = {
                            no.invoke()
                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0, 90, 0))
                        ) {
                            Text(text = "CANCEL",color = Color.White)
                        }
                    }
                }
            }
        }
    }
}