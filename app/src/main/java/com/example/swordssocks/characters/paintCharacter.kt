package com.example.swordssocks.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.swordssocks.R
import com.example.swordssocks.database.User


@Composable
fun CharacterBox(
    hairColor: ColorFilter?,
    hairStyle:Int,
    eye:Int,
    mouth:Int,
    skin:Int,
    size:Int,
    opacity:Float,
    colorTint:ColorFilter?
) {
    val sizeDP = size.dp
    Box(Modifier, Alignment.Center) {
        //Skin
        Image(
            painter = painterResource(skin),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //Hair
        Image(
            painter = painterResource(hairStyle),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP)
                .graphicsLayer(alpha = opacity),
            colorFilter = hairColor
        )
        //eyes
        Image(
            painter = painterResource(eye),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //mouth
        Image(
            painter = painterResource(mouth),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
    }
}

@Composable
fun CharacterDisplay(
    user: User,
    hairColor: ColorFilter?,
    size:Int,
    opacity:Float,
    colorTint:ColorFilter?
) {
    val sizeDP = size.dp
    Box(Modifier, Alignment.Center) {
        //Skin
        Image(
            painter = painterResource(user.draw.skin),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //Hair
        Image(
            painter = painterResource(user.draw.hair),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP)
                .graphicsLayer(alpha = opacity),
            colorFilter = hairColor
        )
        //eyes
        Image(
            painter = painterResource(user.draw.eyes),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //mouth
        Image(
            painter = painterResource(user.draw.mouth),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //Sandals
        if (user.inventory.armors.isNotEmpty()){
            Image(
                painter = painterResource(user.inventory.armors[0].display),
                contentDescription = null,
                modifier = Modifier
                    .size(sizeDP).graphicsLayer(alpha = opacity),
                colorFilter = colorTint,
            )
        }
        //Weapon
        if (user.inventory.meleeWeapons.isNotEmpty()){
            Image(
                painter = painterResource(user.inventory.meleeWeapons[0].equipped),
                contentDescription = null,
                modifier = Modifier
                    .size(sizeDP).graphicsLayer(alpha = opacity),
                colorFilter = colorTint,
            )
        }
    }
}