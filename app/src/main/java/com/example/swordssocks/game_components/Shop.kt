package com.example.swordssocks.game_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.swordssocks.R
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.gladiator_items.*
import com.example.swordssocks.ui.theme.DarkOrange
import com.example.swordssocks.ui.theme.SandColor
import com.example.swordssocks.ui.theme.SandPaper

@Composable
fun WeaponPopup(userRepository: UserRepository,exitFn: ()-> Unit) {
    Popup(alignment = Alignment.Center) {

        var selectedItem: Int by remember { mutableStateOf(0) }
        var displayItem: MeleeWeapon by remember { mutableStateOf(weaponArray[selectedItem]) }


        Row() {
            Box(
                Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp),
                    ),
                contentAlignment = Alignment.Center
            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ){
                    items(weaponArray.size){ index ->
                        if (selectedItem == index){
                            Text(
                                text = weaponArray[index].name,
                                Modifier.border(
                                    width = 2.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(1.dp)
                                ).padding(4.dp)
                            )
                        }else{
                            Text(
                                text = weaponArray[index].name,
                                Modifier.clickable {
                                    displayItem = weaponArray[index]
                                    selectedItem = index
                                }
                            )
                        }
                    }
                }
            }
            Box(
                Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandPaper)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    Modifier.width(100.dp).height(200.dp)
                        .padding(end = 10.dp, top = 45.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(SandColor)
                        .align(Alignment.TopEnd)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                    ,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Pow ${displayItem.power}")
                    Text(text = "Acc ${displayItem.acc}")
                    Text(text = "Crit ${displayItem.criticalChance}%")
                }
                Image(
                    painter = painterResource(id = displayItem.display),
                    contentDescription = null,
                    Modifier
                        .size(200.dp)
                        .padding(end = 50.dp)
                )

                Box(
                    Modifier
                        .size(50.dp)
                        .align(Alignment.TopEnd)
                ) {
                    CheckButton(
                        35,
                        image = Pair(
                            R.drawable.button_cancel_back,
                            R.drawable.button_cancel_front
                        )){
                        exitFn.invoke()
                    }
                }
                Button(
                    onClick = { /*TODO*/ },
                    Modifier
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                ) {
                    Text(text = "Buy (${displayItem.price})", color = Color.White)

                }
            }
        }
    }
}

val weaponArray = arrayOf(
    woodSword,
    ironSword,
    goldSword,
    diamondSword,
    woodAxe,
    ironAxe,
)