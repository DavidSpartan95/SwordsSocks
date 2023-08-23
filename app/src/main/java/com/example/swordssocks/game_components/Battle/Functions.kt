package com.example.swordssocks.game_components.Battle

import com.example.swordssocks.database.User
import com.example.swordssocks.gladiator_items.Potion

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
