package com.example.swordssocks.game_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swordssocks.characters.GenerateFoe
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.HitCharacterAnimation
import com.google.gson.Gson
import kotlinx.coroutines.delay

@Composable
fun ArenaScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User
) {
    var damage by remember{ mutableStateOf(0) }
    var foe by remember { mutableStateOf(GenerateFoe()) }
    var attack by remember { mutableStateOf(Pair(false,"normal")) }
    var attNumUser by remember { mutableStateOf(0) }
    var attNumFoe by remember { mutableStateOf(0) }
    var buttonSelect: Int by remember { mutableStateOf(0) }

    val allButtons = arrayOf(
        arrayOf(
            Triple("Fight",Alignment.TopStart)
            {
                buttonSelect = 1
            },
            Triple("Potions",Alignment.TopEnd){
                buttonSelect = 2
            },
            Triple("Run away",Alignment.BottomStart){
                val userJson = Gson().toJson(user)
                navController.navigate(route = "town_screen/$userJson"){
                    popUpTo(Screen.Arena.route){
                        inclusive = true
                    }
                }
            },
            Triple("Do Nothing",Alignment.BottomEnd){

            }
    ),
        arrayOf(
            Triple("Light Attack ${user.inventory.meleeWeapons[0].acc*1} %",Alignment.TopStart)
            {
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,1.0,1.0)
            },
            Triple("Normal Attack ${user.inventory.meleeWeapons[0].acc*0.9} %",Alignment.TopEnd){
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.9,1.13)
            },
            Triple("Heavy Attack ${user.inventory.meleeWeapons[0].acc*0.8} %",Alignment.BottomStart){
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.8,1.25)
            },
            Triple("Back",Alignment.BottomEnd){
                buttonSelect = 0
            }
        ),
        arrayOf(
            Triple("small potion",Alignment.TopStart)
            {

            },
            Triple("medium potion",Alignment.TopEnd){

            },
            Triple("large potion",Alignment.BottomStart){

            },
            Triple("Back",Alignment.BottomEnd){
                buttonSelect = 0
            }
        ),
        arrayOf()
    )
    var HP by remember { mutableStateOf(user.health*2+10) }
    var foeHP by remember { mutableStateOf(foe.health*2+10) }
    var buttons by remember { mutableStateOf(allButtons[buttonSelect]) }

    LaunchedEffect(buttonSelect,attNumUser){
        if (attack.first){
            attack = Pair(false,"")
            foeHP -= damage
            buttons = allButtons[3]
            //attNumUser++
            //attack= Pair(true,foe.inventory.meleeWeapons[0].element)
            //damage=user.inventory.meleeWeapons[0].attack(user,foe,1.0,1.0)
            delay(1000)
            buttons = allButtons[buttonSelect]
        }else if (!buttons.contentEquals(allButtons[3])){
            buttons = allButtons[buttonSelect]
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center)  {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "HP $HP")
                HitCharacterAnimation(attack,attNumFoe,user)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "HP $foeHP")
                HitCharacterAnimation(attack,attNumUser,foe)
            }
        }
        Box(
            Modifier
                .width(400.dp)
                .fillMaxHeight()
                .background(Color.Black)
                .border(width = 1.dp, color = Color.White,)
        ) {

            for (x in buttons){
                Button(
                    onClick = { x.third.invoke() },
                    Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(0.5f)
                        .align(x.second)
                ) {
                    Text(text = x.first)
                }
            }

        }
    }
}