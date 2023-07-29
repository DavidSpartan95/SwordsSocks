package com.example.swordssocks.game_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.swordssocks.R
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository

@Composable
fun TownScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    user: User,
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier.horizontalScroll(state = scrollState),
    ) {
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
}