package com.example.swordssocks.game_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swordssocks.R
import com.example.swordssocks.characters.CharacterDisplay
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.database.getUserByID
import com.example.swordssocks.database.retrieveAllUsers
import com.example.swordssocks.nav_graph.Screen
import com.google.gson.Gson



@Composable
fun TownScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    userEnter: User,
) {
    var weaponShop by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState(239)
    var user by remember { mutableStateOf(userEnter) }

    LaunchedEffect(weaponShop){
        if (user.id == null){
            val userList = retrieveAllUsers(userRepository)
            user = userList[userList.size-1]
        }else{
            user = getUserByID(userRepository,user.id)
        }
    }
    println(user.draw.hairColor)

    Column(
        Modifier.horizontalScroll(state = scrollState),
    ) {
        //This box paints the Town
        Box() {
            if (weaponShop){
                Box(Modifier.align(Alignment.Center)) {
                    WeaponPopup(user,userRepository){
                        weaponShop = false
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
    if(!weaponShop){
        Box(Modifier.fillMaxSize()) {

            Box(Modifier.align(Alignment.TopCenter)) {

                Row() {
                    CircleButton(picture = R.drawable.button_check_back, text = "Battle"){
                        val userJson = Gson().toJson(user)
                        navController.navigate(route = "arena_screen/$userJson"){
                            popUpTo(Screen.Arena.route){
                                inclusive = true
                            }}
                    }
                    CircleButton(picture = R.drawable.button_cancel_back, text = "Exit"){
                        navController.navigate(route = "home_screen"){
                            popUpTo(Screen.Town.route){
                                inclusive = true
                            }}
                    }
                    CircleButton(picture = R.drawable.button_cancel_back, text = "Shop"){
                        weaponShop = true
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
                .size(100.dp)
                .clickable {
                    onClick.invoke()
                }
        )
        Text(text = text)
    }
}
