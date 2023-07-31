package com.example.swordssocks.game_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.characters.GenerateFoe
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.HitCharacterAnimation
import com.google.gson.Gson

@Composable
fun ArenaScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User
) {
    var attack by remember { mutableStateOf(Pair(false,"normal")) }
    var attNum by remember { mutableStateOf(0) }
    var foe by remember { mutableStateOf(GenerateFoe()) }

    Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center)  {

        HitCharacterAnimation(attack,attNum,user)
        Button(onClick = {
            val userJson = Gson().toJson(user)
            navController.navigate(route = "town_screen/$userJson"){
                popUpTo(Screen.Arena.route){
                    inclusive = true
                }}
        }) {
            Text(text = "GO BACK")
        }
        HitCharacterAnimation(attack,attNum,foe)

    }

}