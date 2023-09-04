package com.example.swordssocks.ui.theme

import androidx.compose.animation.core.*
//import androidx.compose.animation.core.AnimationConstants.Infinite
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.swordssocks.R
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.characters.CharacterDisplay
import com.example.swordssocks.database.User
import kotlinx.coroutines.delay

@Composable
fun HitCharacterAnimation(attack:Pair<Boolean,String>, turn:Int,damage:Pair<Boolean,Int>, user: User) {

    var opacity by remember { mutableStateOf(1f) }
    //var damageNum by remember { mutableStateOf(damage) }
    var showDamage by remember { mutableStateOf(false) }
    var opacityEffect by remember { mutableStateOf(0f) }
    var color by remember { mutableStateOf(Color(255,255,255)) }
    var colorTint: ColorFilter? by remember { mutableStateOf(null) }
    var effect by remember { mutableStateOf(R.drawable.flame) }
    var effectColor by remember { mutableStateOf(Color(255,0,0)) }


    LaunchedEffect(turn){
        //Bellow are all animation effects based on type
        if (attack.first){
            showDamage = true

            opacityEffect = 0f
            colorTint  = null
            repeat(10) {
                delay(50)
                opacity = 1f - opacity
            }
            opacity = 1f
            /*when (attack.second){
                "normal" -> {
                    opacityEffect = 0f
                    colorTint  = null
                    repeat(10) {
                        delay(50)
                        opacity = 1f - opacity
                    }
                    opacity = 1f
                }
                "" -> {
                    opacityEffect = 0f
                    colorTint  = null
                    repeat(10) {
                        delay(50)
                        opacity = 1f - opacity
                    }
                    opacity = 1f
                }
                "heal" -> {
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
                "grass" -> {
                    effect = R.drawable.leaf
                    var r = 255
                    var b = 255
                    effectColor = Color(0,155,10)
                    opacityEffect = 1f
                    repeat(255) {
                        r--
                        b--
                        delay(1)
                        color = Color(r,255,b)
                        colorTint  = ColorFilter.tint(color)
                    }
                    colorTint  = null
                    color = Color(255,255,255)
                    opacityEffect = 0f
                }
                "spark"->{
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
            }*/
            showDamage = false
        }
    }

    Box(Modifier, Alignment.Center) {

        CharacterDisplay(
            hairColor = ColorFilter.tint(user.draw.hairColor),
            user = user,
            size = 200,
            opacity = opacity,
            colorTint = colorTint
        )
        //Animation effect image
        Image(
            painter = painterResource(effect),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer(alpha = opacityEffect),
            colorFilter = ColorFilter.tint(effectColor),)
        if (showDamage){
            if (damage.second>0){
                if (damage.first){
                    Image(
                        painter = painterResource(R.drawable.critmarker),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp))
                    Text(text = damage.second.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                }
                else{
                    Image(
                        painter = painterResource(R.drawable.damager_marker),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp))
                    Text(text = damage.second.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            if (damage.second == 0){
                Image(
                    painter = painterResource(R.drawable.miss),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                    colorFilter = ColorFilter.tint(Color(167,161,161))
                )
                Text(text = "MISS", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if(damage.second < 0){
                Image(
                    painter = painterResource(R.drawable.heal_effect),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                )
                Text(text = "+${damage.second*-1}", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Composable
fun FlashingImage(imageResId: Int, flashDurationMillis: Int = 500) {
    val imagePainter = painterResource(imageResId)
    val transition = rememberInfiniteTransition()

    val opacity by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = flashDurationMillis),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer(alpha = opacity)
    )
}

