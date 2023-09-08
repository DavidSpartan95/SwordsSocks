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
fun MagePopup(user: User, userRepository: UserRepository, exitFn: ()-> Unit) {
    Popup(alignment = Alignment.Center) {

        var selected: Int by remember { mutableStateOf(0) }
        var potionStock by remember { mutableStateOf(potionArray) }
        var displayImage: Int by remember { mutableStateOf(potionArray[0].display) }
        var price: Int by remember { mutableStateOf(potionArray[0].price) }
        var weaponStock by remember { mutableStateOf(mageArray) }
        var coins: Int by remember { mutableStateOf(user.coins) }
        var infoText by remember{ mutableStateOf(
            arrayOf(
                "Heal: ${potionArray[0].recovery}%",
            )
        )
        }
        var ownedStaff  by remember { mutableStateOf(user.inventory.meleeWeapons) }

        Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .width(150.dp)
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
                        items(potionStock.size+weaponStock.size){ index ->
                            if (index < potionStock.size){
                                if (index == selected){
                                    Text(
                                        text = potionStock[index].name,
                                        Modifier
                                            .border(
                                                width = 2.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(1.dp)
                                            )
                                            .padding(4.dp)
                                    )
                                }
                                else {
                                    Text(
                                        text = potionStock[index].name,
                                        Modifier
                                            .clickable {
                                                selected = index
                                                displayImage = potionStock[index].display
                                                infoText = arrayOf("Heal: ${potionArray[index].recovery}%",)
                                                price = potionArray[index].price
                                            }
                                    )
                                }
                            }else{
                                if (selected == index){
                                    Text(
                                        text = weaponStock[index-potionStock.size].name,
                                        Modifier
                                            .border(
                                                width = 2.dp,
                                                color = Color.Black,
                                                shape = RoundedCornerShape(1.dp)
                                            )
                                            .padding(4.dp)
                                    )
                                }else {
                                    Text(
                                        text = weaponStock[index-potionStock.size].name,
                                        Modifier
                                            .clickable {
                                                selected = index
                                                displayImage = weaponStock[index-potionStock.size].display
                                                infoText = arrayOf(
                                                    "Pow: ${weaponStock[index-potionStock.size].power}",
                                                    "Type: ${weaponStock[index-potionStock.size].element}",
                                                    "Acc: ${weaponStock[index-potionStock.size].acc}",
                                                    "Crit: ${weaponStock[index-potionStock.size].criticalChance}%",
                                                )
                                                price = if (containsWeapon(ownedStaff,weaponStock[index-potionStock.size])){
                                                    0
                                                } else {
                                                    weaponStock[index-potionStock.size].price
                                                }
                                            }
                                    )
                                }
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
                        for (x in infoText){
                            Text(text = x)
                        }
                    }
                    Image(
                        painter = painterResource(id = displayImage),
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
                        onClick = {
                            if (selected < potionStock.size && user.coins >= price){
                                userRepository.performDatabaseOperation(Dispatchers.IO){

                                    userRepository.buyItem(Inventory(arrayListOf(potionStock[selected]), arrayListOf(),arrayListOf()),user)
                                    coins -= price
                                }
                            }else {
                                ownedStaff += weaponStock[selected-potionStock.size]
                                userRepository.performDatabaseOperation(Dispatchers.IO){

                                    userRepository.buyItem(Inventory(arrayListOf(), arrayListOf(weaponStock[selected-potionStock.size]),arrayListOf()),user)
                                    coins -= price
                                    price = 0
                                }
                            }
                        },
                        Modifier
                            .align(Alignment.BottomCenter),
                        colors = ButtonDefaults.buttonColors(backgroundColor = DarkOrange)
                    ) {
                        val text = if (price == 0) "owned" else price.toString()
                        Text(text = "Buy ($text)", color = Color.White)
                    }
                }
            }
        }
    }
}
fun containsWeapon(array: ArrayList<Weapon>, armor: Weapon):Boolean {
    for (x in array){
        if (x.name == armor.name){
            return true
        }
    }
    return false
}
val potionArray = arrayOf(
    smallPotion,
    mediumPotion,
    largePotion
)

var mageArray = arrayOf(
    staffAmber,
    staffEmerald,
    staffGarnet,
    staffDiamond
)