package com.example.swordssocks.game_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swordssocks.R
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.nav_graph.Screen

@Composable
fun TownScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User,
) {
    val scrollState = rememberScrollState(239)

    Column(
        Modifier.horizontalScroll(state = scrollState),
    ) {
        //This box paints the Town
        Box() {
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
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                CharacterBox(
                    hairColor = ColorFilter.tint(user.draw.hairColor),
                    hairStyle = user.draw.hair,
                    eye = user.draw.eyes,
                    mouth = user.draw.mouth,
                    skin = user.draw.skin,
                    size = 150,
                )
            }
        }
    }
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.align(Alignment.TopCenter)) {
            Row() {
                CircleButton(picture = R.drawable.button_check_back, text = "Battle"){

                }
                CircleButton(picture = R.drawable.button_cancel_back, text = "Exit"){
                    navController.navigate(route = "home_screen"){
                        popUpTo(Screen.Town.route){
                            inclusive = true
                        }}
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
            Modifier.size(100.dp).clickable {
                onClick.invoke()
            }
        )
        Text(text = text)
    }
}