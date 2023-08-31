package com.example.swordssocks.game_components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swordssocks.R
import com.example.swordssocks.characters.CharacterDisplay
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.database.getUserByID
import com.example.swordssocks.database.retrieveAllUsers
import com.example.swordssocks.game_components.shop.ArmorPopup
import com.example.swordssocks.game_components.shop.MagePopup
import com.example.swordssocks.game_components.shop.WeaponPopup
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.SandColor
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

@Composable
fun TownScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    userEnter: User,
) {
    var shop:Pair<Boolean,String> by remember { mutableStateOf(Pair(false,"")) }
    val scrollState = rememberScrollState(239)
    var user by remember { mutableStateOf(userEnter) }

    LaunchedEffect(shop,user){
        if (user.id == null){
            val userList = retrieveAllUsers(userRepository)
            user = userList[userList.size-1]
        }else{
            user = getUserByID(userRepository,user.id)
        }
    }
    Column(
        Modifier.horizontalScroll(state = scrollState),
    ) {
        //This box paints the Town
        Box() {
            if (shop.first){
                when(shop.second){
                    "weapon" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            WeaponPopup(user,userRepository){
                                shop = Pair(false,"")
                            }
                        }
                    }
                    "armor" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            ArmorPopup(user,userRepository){
                                shop = Pair(false,"")
                            }
                        }
                    }
                    "mage" -> {
                        Box(Modifier.align(Alignment.Center)) {
                            MagePopup(user,userRepository){
                                shop = Pair(false,"")
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
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
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
    if(!shop.first){
        Box(Modifier.fillMaxSize()) {

            Box(Modifier.align(Alignment.TopCenter)) {

                Row() {
                    CircleButton(picture = R.drawable.button_battle, text = ""){
                        val userJson = Gson().toJson(user)
                        navController.navigate(route = "arena_screen/$userJson"){
                            popUpTo(Screen.Arena.route){
                                inclusive = true
                            }}
                    }
                    CircleButton(picture = R.drawable.button_weaponshop, text = ""){
                        shop = Pair(true,"weapon")
                    }
                    CircleButton(picture = R.drawable.button_armorshop, text = ""){
                        shop = Pair(true,"armor")
                    }
                    CircleButton(picture = R.drawable.button_magic, text = ""){
                        shop = Pair(true,"mage")
                    }
                    CircleButton(picture = R.drawable.button_exit, text = ""){
                        navController.navigate(route = "home_screen"){
                            popUpTo(Screen.Town.route){
                                inclusive = true
                            }}
                    }
                    CircleButton(picture = R.drawable.button_cancel_back, text = "Coins++"){
                        userRepository.performDatabaseOperation(Dispatchers.IO){
                            userRepository.toggleCoins(99999,user.id)

                        }
                    }
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
                        ),
                        Alignment.Center
                    ){
                        Text(
                            text = "Level: ${user.level} Coins:${user.coins}",
                            fontWeight = FontWeight.Bold,
                        )
                    }
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
