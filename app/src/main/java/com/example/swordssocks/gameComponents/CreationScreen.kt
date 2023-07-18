package com.example.swordssocks.gameComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.swordssocks.characters.CharacterBox
import com.example.swordssocks.ui.theme.SandPaper
import com.example.swordssocks.viewModel.CharacterViewModel

var charViewModel = CharacterViewModel()

@Composable
fun CreationScreen() {

    val hairColor by charViewModel.hairColor.collectAsState()

    Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column() {
                AppearanceSelectorBox {

                    SelectorButtons(
                        "Hair Color",
                        {charViewModel.cycleHairColor("backward")},
                        {charViewModel.cycleHairColor("forwards")}
                    )
                    SelectorButtons("???", {println("back")},{println("forward")})
                }
            }
            CharacterBox(hairColor = ColorFilter.tint(hairColor))
        }
    }
}

@Composable
fun AppearanceSelectorBox(content: @Composable () -> Unit) {
    Box(
        Modifier
            .heightIn(100.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(SandPaper)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center,

    ) {
        Column(Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
            content()
        }
    }
}

@Composable
fun SelectorButtons(text:String, ArrowBack:()-> Unit,ArrowForward:()-> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { ArrowBack.invoke() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(text = text)
        IconButton(onClick = { ArrowForward.invoke() }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Back")
        }
    }
}