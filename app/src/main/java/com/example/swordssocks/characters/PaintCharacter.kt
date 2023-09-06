package com.example.swordssocks.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swordssocks.database.Inventory
import com.example.swordssocks.database.User


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
    }
}

@Composable
fun CharacterDisplay(
    user: User,
    hairColor: ColorFilter?,
    size:Int,
    opacity:Float,
    colorTint:ColorFilter?
) {
    val sizeDP = size.dp
    Box(Modifier, Alignment.Center) {
        //Skin
        Image(
            painter = painterResource(user.draw.skin),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP)
                .graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //Hair
        if (!hasHelmet(user.inventory)){
            Image(
                painter = painterResource(user.draw.hair),
                contentDescription = null,
                modifier = Modifier
                    .size(sizeDP)
                    .graphicsLayer(alpha = opacity),
                colorFilter = hairColor
            )
        }
        //eyes
        Image(
            painter = painterResource(user.draw.eyes),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //mouth
        Image(
            painter = painterResource(user.draw.mouth),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDP).graphicsLayer(alpha = opacity),
            colorFilter = colorTint,
        )
        //Socks
        if (user.inventory.armors.isNotEmpty()){
            for (x in user.inventory.armors){
                if (x.part == "Socks"){
                    Image(
                        painter = painterResource(x.equipped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(sizeDP).graphicsLayer(alpha = opacity),
                        colorFilter = colorTint,
                    )
                    break
                }
            }
            //Sandals
            for (x in user.inventory.armors){
                if (x.part == "Sandal"){
                    Image(
                        painter = painterResource(x.equipped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(sizeDP).graphicsLayer(alpha = opacity),
                        colorFilter = colorTint,
                    )
                    break
                }
            }
            //Chest
            for (x in user.inventory.armors){
                if (x.part == "Chest"){
                    Image(
                        painter = painterResource(x.equipped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(sizeDP).graphicsLayer(alpha = opacity),
                        colorFilter = colorTint,
                    )
                    break
                }
            }
            //Helmet
            for (x in user.inventory.armors){
                if (x.part == "Helmet"){
                    Image(
                        painter = painterResource(x.equipped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(sizeDP).graphicsLayer(alpha = opacity),
                        colorFilter = colorTint,
                    )
                    break
                }
            }
            //Leggings
            for (x in user.inventory.armors){
                if (x.part == "Leggings"){
                    Image(
                        painter = painterResource(x.equipped),
                        contentDescription = null,
                        modifier = Modifier
                            .size(sizeDP).graphicsLayer(alpha = opacity),
                        colorFilter = colorTint,
                    )
                    break
                }
            }
        }
        //Weapon
        if (user.inventory.meleeWeapons.isNotEmpty()){
            if (user.inventory.meleeWeapons[0].display !=0){
                Image(
                    painter = painterResource(user.inventory.meleeWeapons[0].equipped),
                    contentDescription = null,
                    modifier = Modifier
                        .size(sizeDP).graphicsLayer(alpha = opacity),
                    colorFilter = colorTint,
                )
            }
        }
    }
}
fun hasHelmet(inventory:Inventory): Boolean{
    for (x in inventory.armors){
        if (x.part == "Helmet"){
            return true
        }
    }
    return false
}
