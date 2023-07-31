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
        //socks
        /*Image(
            painter = painterResource(R.drawable.character_socks_3),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP),
        )*/
        //Sandals
        Image(
            painter = painterResource(R.drawable.character_sandals_1),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
    }
}