package com.example.swordssocks.game_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.swordssocks.database.User
import com.example.swordssocks.nav_graph.Screen
import com.example.swordssocks.ui.theme.DarkOrange
import com.example.swordssocks.ui.theme.SandPaper

@Composable
fun InfoPopUp(user:User, back:()->Unit) {
    Popup(alignment = Alignment.Center) {
        Box(
            Modifier
                .height(400.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(SandPaper)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center,
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "EXP to next lvl:${((user.level+1)*(user.level+1)*(user.level+1))-user.exp}")
                Text(text = "HP ${user.health}")
                Text(text = "Strength ${user.strength}")
                Text(text = "Defence ${user.defence}")
                Text(text = "Charisma ${user.charisma}")
                Text(text = "Magic ${user.magic}")
                Button(
                    onClick = {
                        back.invoke()
                    },//colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                ){
                    Text(text = "Back")
                }
            }
        }
    }
}