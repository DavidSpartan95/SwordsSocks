package com.example.swordssocks.game_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.swordssocks.characters.AnimatableCharacter


@Composable
fun AnimationTest() {
    Column(Modifier.fillMaxWidth(), Arrangement.Top, Alignment.CenterHorizontally) {

        var attack by remember { mutableStateOf(Pair(false,"normal")) }
        var attNum by remember { mutableStateOf(0) }

        AnimatableCharacter(attack,attNum)

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
        Button(onClick = {
        }) {
            Text(text = "Nothing")
        }
    }
}