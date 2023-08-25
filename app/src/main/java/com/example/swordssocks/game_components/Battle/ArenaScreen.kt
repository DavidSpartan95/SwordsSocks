package com.example.swordssocks.game_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swordssocks.characters.generateFoe
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.Battle.LevelUpPopUp
import com.example.swordssocks.game_components.Battle.findAllPotions
import com.example.swordssocks.gladiator_items.smallPotion
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.HitCharacterAnimation
import com.example.swordssocks.ui.theme.SandPaper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@Composable
fun ArenaScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User
) {
    var gameOver by remember{ mutableStateOf(Pair(false,"")) }
    var levelUp by remember{ mutableStateOf(Pair(false,0)) }
    var levelPopUp by remember{ mutableStateOf(false) }
    var potions by remember{ mutableStateOf(findAllPotions(user)) }
    var damage by remember{ mutableStateOf(Pair(false,0)) }
    var foe by remember { mutableStateOf(generateFoe(user.level)) }
    var attack by remember { mutableStateOf(Pair(false,"normal")) }
    var attNumUser by remember { mutableStateOf(0) }
    var attNumFoe by remember { mutableStateOf(0) }
    var buttonSelect: Int by remember { mutableStateOf(0) }
    var HP by remember { mutableStateOf(user.health*2+10) }
    var foeHP by remember { mutableStateOf(foe.health*2+10) }

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
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.9,1.40)
            },
            Triple("Heavy Attack ${user.inventory.meleeWeapons[0].acc*0.8} %",Alignment.BottomStart){
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.8,1.75)
            },
            Triple("Back",Alignment.BottomEnd){
                buttonSelect = 0
            }
        ),
        arrayOf(
            Triple("small potion(${potions.first})",Alignment.TopStart)
            {
                if (potions.first > 0) {
                    if (smallPotion.heal((user.health*2+10))+HP > (user.health*2+10)){
                        HP = user.health*2+10
                    }else{
                        HP += smallPotion.heal((user.health*2+10))
                    }
                    damage = Pair(false,-smallPotion.heal((user.health*2+10)))
                    attack = Pair(true,"heal")
                }
            },
            Triple("medium potion(${potions.second})",Alignment.TopEnd){

            },
            Triple("large potion(${potions.third})",Alignment.BottomStart){

            },
            Triple("Back",Alignment.BottomEnd){
                buttonSelect = 0
            }
        ),
        arrayOf()
    )
    var buttons by remember { mutableStateOf(allButtons[buttonSelect]) }

    LaunchedEffect(buttonSelect,attack){
        if (!gameOver.first){
            if (attack.first && attack.second != "heal" && attack.second != ""){
                foeHP -= damage.second
                buttons = allButtons[3]
                delay(1000)
                if (foeHP <= 0) gameOver = Pair(true,"win")
                //Foe Attack
                attNumFoe++
                attack= Pair(true,foe.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(foe,user,1.0,1.0)
                HP -= damage.second
                delay(1000)
                if (HP <= 0) gameOver = Pair(true,"lose")
                buttons = allButtons[0]
                attack = Pair(false,"")
            }
            if (attack.first && attack.second == "heal"){
                attNumFoe++
                buttons = allButtons[3]
                delay(1000)
                //Foe attack
                attNumFoe++
                damage=user.inventory.meleeWeapons[0].attack(foe,user,1.0,1.0)
                HP -= damage.second
                delay(1000)
                if (HP <= 0) gameOver = Pair(true,"lose")
                buttons = allButtons[0]
                attack = Pair(false,"")
            }
            else if (!buttons.contentEquals(allButtons[3])){
                buttons = allButtons[buttonSelect]
            }
        }
    }

    if (!gameOver.first){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center)  {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "HP $HP")
                    HitCharacterAnimation(attack,attNumFoe,damage,user)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "HP $foeHP")
                    HitCharacterAnimation(attack,attNumUser,damage,foe)
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
    }else if(gameOver.first && gameOver.second == "win"){
        if (levelPopUp){
            LevelUpPopUp(userRepository,user = user, skillPoints = (12*levelUp.second)) {
                levelPopUp = false
                val userJson = Gson().toJson(user)
                navController.navigate(route = "town_screen/$userJson") {
                    popUpTo(Screen.Arena.route) {
                        inclusive = true
                    }
                }
            }
        }
        userRepository.performDatabaseOperation(Dispatchers.IO){
            if (!levelUp.first){
                levelUp = userRepository.levelUp((foe.exp+(foe.exp*(user.charisma/100))),user.id)
            }
            userRepository.toggleCoins(foe.coins+(foe.coins*(user.charisma/100)),user.id)
        }
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .heightIn(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,
            ){
                Column() {
                    Text(text = "You Win")
                    Text(text = "EXP ${(foe.exp+(foe.exp*(user.charisma/100)))} (${user.charisma}% CHA)")
                    Text(text = "Coins ${(foe.coins)} (${user.charisma}% CHA)")
                    Button(onClick = {
                        levelPopUp = true
                    }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }else if(gameOver.first && gameOver.second == "lose"){
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .heightIn(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center,
            ){
                Column() {
                    Text(text = "You Lose")
                    Button(onClick = {
                        val userJson = Gson().toJson(user)
                        navController.navigate(route = "town_screen/$userJson"){
                            popUpTo(Screen.Arena.route){
                                inclusive = true
                            }
                        }
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}