package com.example.swordssocks.game_components.shop

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.swordssocks.R
import com.example.swordssocks.database.Inventory
import com.example.swordssocks.database.User
import com.example.swordssocks.database.UserRepository
import com.example.swordssocks.game_components.CheckButton
import com.example.swordssocks.gladiator_items.*
import com.example.swordssocks.ui.theme.DarkOrange
import com.example.swordssocks.ui.theme.SandColor
import com.example.swordssocks.ui.theme.SandPaper
import kotlinx.coroutines.Dispatchers

@Composable
fun ArmorPopup(user: User, userRepository: UserRepository, exitFn: ()-> Unit) {
    Popup(alignment = Alignment.Center) {

        var selectedItem: Int by remember { mutableStateOf(0) }
        var displayItem: Armor by remember { mutableStateOf(armorShop[selectedItem]) }
        val armorStock by remember { mutableStateOf(armorShop)}
        var coins: Int by remember { mutableStateOf(user.coins) }
        var ownedArmor by remember { mutableStateOf(user.inventory.armors) }

        Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .padding(end = 10.dp, top = 10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(SandColor)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    ),
                Alignment.Center
            ) {
                Text(
                    text = "Level: ${user.level} Coins:${coins}",
                    fontWeight = FontWeight.Bold,
                )
            }
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
                        items(armorStock.size){ index ->
                            if (selectedItem == index){
                                Text(
                                    text = armorStock[index].name,
                                    Modifier
                                        .border(
                                            width = 2.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(1.dp)
                                        )
                                        .padding(4.dp)
                                )
                            }else{
                                Text(
                                    text = armorStock[index].name,
                                    Modifier.clickable {
                                        displayItem = armorStock[index]
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
                        Modifier
                            .width(100.dp)
                            .height(200.dp)
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
                        Text(text = displayItem.part)
                        Text(text = "Armor: ${displayItem.armor}")
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
                    if (!containsArmor(ownedArmor,displayItem)){
                        Button(
                            onClick = {
                                ownedArmor += displayItem
                                userRepository.performDatabaseOperation(Dispatchers.IO){
                                    if (user.coins >= displayItem.price){
                                        userRepository.buyItem(Inventory(arrayListOf(), arrayListOf(),arrayListOf(displayItem)),user)
                                        coins -= displayItem.price
                                    }
                                }
                            },
                            Modifier
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                        ) {
                            Text(text = "Buy (${displayItem.price})", color = Color.White)

                        }
                    }else {
                        Button(
                            onClick = {
                            },
                            Modifier
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                        ) {
                            Text(text = "Owned", color = Color.White)

                        }
                    }
                }
            }
        }

    }
}

fun containsArmor(array: ArrayList<Armor>, armor: Armor):Boolean {
    for (x in array){
        if (x.name == armor.name){
            return true
        }
    }
    return false
}
val armorShop = arrayOf(
    basicSandal,
    normalSandal,
    clogs,
    basicSocks,
    normalSocks,
    greatSocks,
    leatherChestplate,
    ironChestplate,
    goldChestplate,
    leatherHelmet,
    goldHelmet,
    leatherLeggings,
    ironLeggings,
    goldLeggings
)