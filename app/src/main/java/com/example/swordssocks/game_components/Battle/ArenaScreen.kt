package com.example.swordssocks.game_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import com.example.swordssocks.characters.generateFoe
import com.example.swordssocks.characters.level10Boss
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.Battle.LevelUpPopUp
import com.example.swordssocks.game_components.Battle.findAllPotions
import com.example.swordssocks.game_components.Battle.playerArmorValue
import com.example.swordssocks.gladiator_items.largePotion
import com.example.swordssocks.gladiator_items.mediumPotion
import com.example.swordssocks.gladiator_items.smallPotion
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ButtonInfo(var position:Alignment, var color:Color)
@Composable
fun ArenaScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User
) {
    var receivedEXP by remember{ mutableStateOf(false) }
    var gameOver by remember{ mutableStateOf(Pair(false,"")) }
    var levelUp by remember{ mutableStateOf(Pair(false,0)) }
    var levelPopUp by remember{ mutableStateOf(false) }
    var potions by remember{ mutableStateOf(findAllPotions(user)) }
    var damage by remember{ mutableStateOf(Pair(false,0)) }
    val foe by remember { mutableStateOf(generateFoe(user.level)) }
    var attack by remember { mutableStateOf(Pair(false,"normal")) }
    var attNumUser by remember { mutableStateOf(0) }
    var attNumFoe by remember { mutableStateOf(0) }
    var buttonSelect: Int by remember { mutableStateOf(0) }
    var HP by remember { mutableStateOf(user.health*2+10) }
    var armor by remember { mutableStateOf(playerArmorValue(user)) }
    var foeArmor by remember { mutableStateOf(playerArmorValue(foe)) }
    var foeHP by remember { mutableStateOf(foe.health*2+10) }
    val allButtons = arrayOf(
        arrayOf(
            Triple("Fight",ButtonInfo(Alignment.TopStart,Color.Red))
            {
                buttonSelect = 1
            },
            Triple("Potions",ButtonInfo(Alignment.TopEnd,DarkGreen)){
                buttonSelect = 2
            },
            Triple("Run away",ButtonInfo(Alignment.BottomStart,Color.Blue)){
                val userJson = Gson().toJson(user)
                navController.navigate(route = "town_screen/$userJson"){
                    popUpTo(Screen.Arena.route){
                        inclusive = true
                    }
                }
            },
    ),
        arrayOf(
            Triple("Light Attack ${user.inventory.meleeWeapons[0].acc*1} %",ButtonInfo(Alignment.TopStart,Color.Red))
            {
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,1.0,1.0)
            },
            Triple("Normal Attack ${user.inventory.meleeWeapons[0].acc*0.9} %",ButtonInfo(Alignment.TopEnd,DarkGreen)){
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.9,1.40)
            },
            Triple("Heavy Attack ${user.inventory.meleeWeapons[0].acc*0.8} %",ButtonInfo(Alignment.BottomStart,Color.Blue)){
                attNumUser++
                attack= Pair(true,user.inventory.meleeWeapons[0].element)
                damage=user.inventory.meleeWeapons[0].attack(user,foe,0.8,1.75)
            },
            Triple("Back",ButtonInfo(Alignment.BottomEnd,DarkOrange)){
                buttonSelect = 0
            }
        ),
        arrayOf(
            Triple("small potion(${potions.first})",ButtonInfo(Alignment.TopStart,Color.Red))
            {
                if (potions.first > 0) {
                    userRepository.performDatabaseOperation(Dispatchers.IO){
                        userRepository.togglePotion(smallPotion,user.id,"remove")
                    }
                    if (smallPotion.heal((user.health*2+10))+HP > (user.health*2+10)){
                        HP = user.health*2+10
                    }else{
                        HP += smallPotion.heal((user.health*2+10))
                    }
                    damage = Pair(false,-smallPotion.heal((user.health*2+10)))
                    attack = Pair(true,"heal")
                    potions = Triple(potions.first-1,potions.second,potions.third)
                }
            },
            Triple("medium potion(${potions.second})",ButtonInfo(Alignment.TopEnd,DarkGreen)){
                if (potions.second > 0) {
                    userRepository.performDatabaseOperation(Dispatchers.IO){
                        userRepository.togglePotion(mediumPotion,user.id,"remove")
                    }
                    if (mediumPotion.heal((user.health*2+10))+HP > (user.health*2+10)){
                        HP = user.health*2+10
                    }else{
                        HP += mediumPotion.heal((user.health*2+10))
                    }
                    damage = Pair(false,-mediumPotion.heal((user.health*2+10)))
                    attack = Pair(true,"heal")
                    potions = Triple(potions.first,potions.second-1,potions.third)
                }

            },
            Triple("large potion(${potions.third})",ButtonInfo(Alignment.BottomStart,Color.Blue)){
                if (potions.third > 0) {
                    userRepository.performDatabaseOperation(Dispatchers.IO){
                        userRepository.togglePotion(largePotion,user.id,"remove")
                    }
                    if (largePotion.heal((user.health*2+10))+HP > (user.health*2+10)){
                        HP = user.health*2+10
                    }else{
                        HP += largePotion.heal((user.health*2+10))
                    }
                    damage = Pair(false,-largePotion.heal((user.health*2+10)))
                    attack = Pair(true,"heal")
                    potions = Triple(potions.first,potions.second,potions.third-1)
                }

            },
            Triple("Back",ButtonInfo(Alignment.BottomEnd,DarkOrange)){
                buttonSelect = 0
            }
        ),
        arrayOf()
    )
    var buttons by remember { mutableStateOf(allButtons[buttonSelect]) }

    LaunchedEffect(buttonSelect,attack){
        if (!gameOver.first){
            if (attack.first && attack.second != "heal" && attack.second != ""){
                if (foeArmor > 0){
                    foeArmor -= damage.second
                }else{
                    foeHP -= damage.second
                }
                buttons = allButtons[3]
                delay(1000)
                if (foeHP <= 0){
                    gameOver = Pair(true,"win")
                }else{
                    //Foe Attack
                    attNumFoe++
                    damage=user.inventory.meleeWeapons[0].attack(foe,user,1.0,1.0)
                    Log.d("ATK", "my Message")
                    if (armor > 0){
                        armor -= damage.second
                    }else{
                        HP -= damage.second
                    }
                    delay(1000)
                    if (HP <= 0) gameOver = Pair(true,"lose")
                    attack = Pair(false,"")
                    buttons = allButtons[0]
                }
            }
            if (attack.first && attack.second == "heal"){
                Log.d("Heal", "my Message")
                attNumFoe++
                buttons = allButtons[3]
                delay(1000)
                //Foe attack
                attNumFoe++
                damage=user.inventory.meleeWeapons[0].attack(foe,user,1.0,1.0)
                if (armor > 0){
                    armor -= damage.second
                }else{
                    HP -= damage.second
                }
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
                    HP_bar(currentHP = HP, maxHP = (user.health*2+10), armor, playerArmorValue(user))
                    HitCharacterAnimation(attack,attNumFoe,damage,user)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HP_bar(currentHP = foeHP, maxHP = (foe.health*2+10), foeArmor, playerArmorValue(foe))
                    HitCharacterAnimation(attack,attNumUser,damage,foe)
                }
            }
            Box(
                Modifier
                    .width(400.dp)
                    .height(300.dp)
                    .background(SandColor)
                    .border(width = 1.dp, color = Color.White,)
            ) {

                for (x in buttons){
                    Button(
                        onClick = { x.third.invoke() },
                        Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth(0.5f)
                            .align(x.second.position),
                        colors = ButtonDefaults.buttonColors(backgroundColor = x.second.color)
                    ) {
                        Text(text = x.first, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

            }
        }
    }else if(gameOver.first && gameOver.second == "win"){
        if (levelPopUp){
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LevelUpPopUp(userRepository,user = user, skillPoints = (12*levelUp.second)) {

                    userRepository.performDatabaseOperation(Dispatchers.IO){
                        val userJson = Gson().toJson(userRepository.getById(user.id))
                        CoroutineScope(Dispatchers.Main).launch {
                            navController.navigate(route = "town_screen/$userJson") {
                                popUpTo(Screen.Arena.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }else{
            userRepository.performDatabaseOperation(Dispatchers.IO){
                if (!receivedEXP){
                    if (!levelUp.first){
                        levelUp = userRepository.levelUp((foe.exp+(foe.exp*(user.charisma/100))),user.id)
                    }
                    userRepository.toggleCoins(foe.coins+(foe.coins*(user.charisma/100)),user.id)
                    receivedEXP = true
                }
            }
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .height(250.dp)
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
                    Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "You Win", fontWeight = FontWeight.Bold )
                        Text(text = "EXP ${(foe.exp+(foe.exp*(user.charisma/100)))} (${user.charisma}% CHA)" , fontWeight = FontWeight.Bold)
                        Text(text = "Coins ${(foe.coins)} (${user.charisma}% CHA)", fontWeight = FontWeight.Bold)
                        Button(onClick = {
                            if (levelUp.first){
                                levelPopUp = true
                            }else{
                                userRepository.performDatabaseOperation(Dispatchers.IO){
                                    val userJson = Gson().toJson(userRepository.getById(user.id))
                                    CoroutineScope(Dispatchers.Main).launch {
                                        navController.navigate(route = "town_screen/$userJson") {
                                            popUpTo(Screen.Arena.route) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            }
                        },
                            //colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                        ) {
                            Text(text = "OK")
                        }
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

@Composable
fun HP_bar(currentHP:Int, maxHP:Int, currentArmor: Int, maxArmor: Int) {

    val greenBar = ((currentHP.toDouble()/maxHP.toDouble())*100)
    val greyBar = ((currentArmor.toDouble()/maxArmor.toDouble())*100)
    var armorBarColor = Color.Gray
    var armorTextColor = Color.White

    if ( currentArmor <= 0){
        armorBarColor = SandColor
        armorTextColor = SandColor
    }
        Box() {
            Box(
                Modifier
                    .width(greyBar.dp)
                    .height(20.dp)
                    .background(armorBarColor)
            ) {

            }
            Text(text = currentArmor.toString(), Modifier.align(Alignment.Center), color = armorTextColor)
        }

    Box() {
        Box(
            Modifier
                .width(100.dp)
                .height(20.dp)
                .background(Color.Gray)) {

        }
        Box(
            Modifier
                .width(greenBar.dp)
                .height(20.dp)
                .background(Color.Green)) {
        }
        Text(text = currentHP.toString(), Modifier.align(Alignment.Center))
    }
}