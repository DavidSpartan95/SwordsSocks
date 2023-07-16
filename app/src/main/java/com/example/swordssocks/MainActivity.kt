package com.example.swordssocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swordssocks.ui.theme.SwordsSocksTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwordsSocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Game()
                }
            }
        }
    }
}

@Composable
fun Game() {

    Column(Modifier.fillMaxWidth(), Arrangement.Top, CenterHorizontally) {

        var attack by remember { mutableStateOf(Pair(false,"normal")) }
        var attNum by remember { mutableStateOf(0) }

        Character(attack,attNum)

        Button(onClick = {
            attack = Pair(true,"normal")
            attNum++

        }) {
            Text(text = "Normal")
        }
        Button(onClick = {
            attack = Pair(true,"fire")
            attNum++
        }) {
            Text(text = "Fire")
        }
        Button(onClick = {
            attack = Pair(true,"water")
            attNum++
        }) {
            Text(text = "Water")
        }
        Button(onClick = {
            attack = Pair(true,"electric")
            attNum++
        }) {
            Text(text = "Electric")
        }
    }

}
@Composable
fun Character(attack:Pair<Boolean,String>, num:Int) {

    var opacity by remember { mutableStateOf(1f) }
    var opacityEffect by remember { mutableStateOf(0f) }
    var color by remember { mutableStateOf(Color(255,255,255)) }
    var colorTint: ColorFilter? by remember { mutableStateOf(null) }
    var effect by remember { mutableStateOf(R.drawable.flame) }
    var effectColor by remember { mutableStateOf(Color(255,0,0)) }

    LaunchedEffect(num){

        if (attack.first){
            when (attack.second){
                "normal" -> {
                    colorTint  = null
                    repeat(10) {
                        delay(50)
                        opacity = 1f - opacity
                    }
                    //opacity = 1f
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
        Image(
            painter = painterResource(R.drawable.monster),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint
        )

        Image(
            painter = painterResource(effect),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .graphicsLayer(alpha = opacityEffect),
            colorFilter = ColorFilter.tint(effectColor)
        )


    }
    
}


