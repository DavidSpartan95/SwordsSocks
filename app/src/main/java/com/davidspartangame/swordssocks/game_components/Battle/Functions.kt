package com.davidspartangame.swordssocks.game_components.Battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.davidspartangame.swordssocks.database.User
import com.davidspartangame.swordssocks.gladiator_items.Potion

fun findAllPotions(user: User):Triple<Int,Int,Int>{
    var smallPotion:Int = 0
    var mediumPotion:Int = 0
    var largePotion:Int = 0
    for (x in user.inventory.potions){
        when(x.name){
            "small potion" -> smallPotion++
            "medium potion" -> mediumPotion++
            "large potion" -> largePotion++
        }
    }
    return Triple(smallPotion,mediumPotion,largePotion)
}

fun playerArmorValue(user: User): Int{

    var armor: Int = 0
    if (user.inventory.armors.isNotEmpty()){
        for (x in user.inventory.armors){
            if (x.part == "Socks"){
                armor += x.armor
                break
            }
        }
        //Sandals
        for (x in user.inventory.armors){
            if (x.part == "Sandal"){
                armor += x.armor
                break
            }
        }
        //Chest
        for (x in user.inventory.armors){
            if (x.part == "Chest"){
                armor += x.armor
                break
            }
        }
        //Helmet
        for (x in user.inventory.armors){
            if (x.part == "Helmet"){
                armor += x.armor
                break
            }
        }
        //Leggings
        for (x in user.inventory.armors){
            if (x.part == "Leggings"){
                armor += x.armor
                break
            }
        }
    }
    return armor
}
