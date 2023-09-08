package com.davidspartangame.swordssocks.game_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.davidspartangame.swordssocks.R
import com.davidspartangame.swordssocks.characters.CharacterDisplay
import com.davidspartangame.swordssocks.database.User
import com.davidspartangame.swordssocks.database.UserRepository
import com.davidspartangame.swordssocks.database.getUserByID
import com.davidspartangame.swordssocks.database.retrieveAllUsers
import com.davidspartangame.swordssocks.game_components.shop.ArmorPopup
import com.davidspartangame.swordssocks.game_components.shop.MagePopup
import com.davidspartangame.swordssocks.game_components.shop.WeaponPopup
import com.davidspartangame.swordssocks.nav_graph.Screen
import com.davidspartangame.swordssocks.ui.theme.SandColor
import com.google.gson.Gson

@Composable
fun TownScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    userEnter: User,
) {
    var popUp:Pair<Boolean,String> by remember { mutableStateOf(Pair(false,"")) }
    val scrollState = rememberScrollState(239)
    var user by remember { mutableStateOf(userEnter) }

    LaunchedEffect(popUp){
        if (user.id == null){
            val userList = retrieveAllUsers(userRepository)
            user = userList[userList.size-1]
        }else if (!popUp.first){
            user = getUserByID(userRepository,user.id)
        }
    }
    Column(
        Modifier.horizontalScroll(state = scrollState),
    ) {
        //This box paints the Town
        Box() {
            if (popUp.first){
                when(popUp.second){
                    "weapon" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            WeaponPopup(user,userRepository){
                                popUp = Pair(false,"")
                            }
                        }
                    }
                    "armor" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            ArmorPopup(user,userRepository){
                                popUp = Pair(false,"")
                            }
                        }
                    }
                    "mage" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            MagePopup(user,userRepository){
                                popUp = Pair(false,"")
                            }
                        }
                    }
                    "info" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            InfoPopUp(user){
                                popUp = Pair(false,"")
                            }
                        }
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.village_backdrop_temp),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.village_arena_temp),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.village_armory_temp),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.village_weaponsmith_temp),
                contentDescription = null
            )
            user?.let {
                Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp)) {
                    CharacterDisplay(
                        hairColor = ColorFilter.tint(user.draw.hairColor),
                        user = user,
                        size = 150,
                        opacity = 1.0F,
                        colorTint = null
                    )
                }
            }
        }
    }
    if(!popUp.first){
        Box(Modifier.fillMaxSize()) {

            Box(Modifier.align(Alignment.TopCenter)) {
                Box(Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .padding(end = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandColor)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ).clickable {
                                popUp = Pair(true,"info")
                    },
                    Alignment.Center
                ){
                    Text(
                        text = "Level: ${user.level} Coins:${user.coins}",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Box(Modifier.align(Alignment.BottomCenter)) {

                Row() {
                    CircleButton(picture = R.drawable.button_battle, text = ""){
                        val userJson = Gson().toJson(user)
                        navController.navigate(route = "arena_screen/$userJson"){
                            popUpTo(Screen.Arena.route){
                                inclusive = true
                            }}
                    }
                    CircleButton(picture = R.drawable.button_weaponshop, text = ""){
                        popUp = Pair(true,"weapon")
                    }
                    CircleButton(picture = R.drawable.button_armorshop, text = ""){
                        popUp = Pair(true,"armor")
                    }
                    CircleButton(picture = R.drawable.button_magic, text = ""){
                        popUp = Pair(true,"mage")
                    }
                    CircleButton(picture = R.drawable.button_exit, text = ""){
                        navController.navigate(route = "home_screen"){
                            popUpTo(Screen.Town.route){
                                inclusive = true
                            }}
                    }
                   /* CircleButton(picture = R.drawable.button_cancel_back, text = "Coins++"){
                        userRepository.performDatabaseOperation(Dispatchers.IO){
                            userRepository.toggleCoins(99999,user.id)

                        }
                    }*/
                }
            }
        }
    }
}
@Composable
fun CircleButton(picture:Int,text: String, onClick: ()-> Unit) {
    Box(Modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = picture),
            contentDescription = null,
            Modifier
                .size(75.dp)
                .clickable {
                    onClick.invoke()
                }
        )
        Text(text = text)
    }
}
