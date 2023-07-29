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
fun AnimatableCharacter(attack:Pair<Boolean,String>, num:Int) {

    var opacity by remember { mutableStateOf(1f) }
    var opacityEffect by remember { mutableStateOf(0f) }
    var color by remember { mutableStateOf(Color(255,255,255)) }
    var colorTint: ColorFilter? by remember { mutableStateOf(null) }
    var effect by remember { mutableStateOf(R.drawable.flame) }
    var effectColor by remember { mutableStateOf(Color(255,0,0)) }

    LaunchedEffect(num){
        //Bellow are all animation effects based on type
        if (attack.first){
            when (attack.second){
                "normal" -> {
                    opacityEffect = 0f
                    colorTint  = null
                    repeat(10) {
                        delay(50)
                        opacity = 1f - opacity
                    }
                    opacity = 1f
                }
                "fire" -> {
                    effect = R.drawable.flame
                    effectColor = Color(255,0,0)
                    color = Color(100,100,100)
                    repeat(10) {
                        delay(50)
                        opacityEffect = 1f - opacityEffect
                        colorTint  = ColorFilter.tint(color)
                    }
                    colorTint  = null
                    opacityEffect = 0f
                }
                "water" -> {
                    effect = R.drawable.water
                    var r = 255
                    var g = 255
                    effectColor = Color(0,155,255)
                    opacityEffect = 1f
                    repeat(255) {
                        r--
                        g--
                        delay(1)
                        color = Color(r,g,255)
                        colorTint  = ColorFilter.tint(color)
                    }
                    colorTint  = null
                    color = Color(255,255,255)
                    opacityEffect = 0f
                }
                "electric"->{
                    effect = R.drawable.electric
                    effectColor = Color(255,211,0)
                    color = Color(200,200,200)
                    opacityEffect = 1f
                    repeat(6) {
                        delay(100)
                        opacity = 1f - opacity
                        colorTint  = ColorFilter.tint(color)
                    }
                    colorTint  = null
                    opacityEffect = 0f
                }
            }
        }
    }

    Box(Modifier, Alignment.Center) {
        //Body
        Image(
            painter = painterResource(R.drawable.character_base_tan),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint
        )
        //Hair
        Image(
            painter = painterResource(R.drawable.character_hair_1),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint
        )
        //Animation effect image
        Image(
            painter = painterResource(effect),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer(alpha = opacityEffect),
            colorFilter = ColorFilter.tint(effectColor),)
    }
}

@Composable
fun CharacterBox(hairColor: ColorFilter?, hairStyle:Int,eye:Int,mouth:Int,skin:Int, size:Int) {
    val sizeDP = size.dp
    Box(Modifier, Alignment.Center) {
        //Skin
        Image(
            painter = painterResource(skin),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP),
        )
        //Hair
        Image(
            painter = painterResource(hairStyle),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP),
            colorFilter = hairColor
        )
        //eyes
        Image(
            painter = painterResource(eye),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP),
        )
        //mouth
        Image(
            painter = painterResource(mouth),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP),
        )
    }
}