package com.davidspartangame.swordssocks.gladiator_items

import com.davidspartangame.swordssocks.R

class Potion(
    val name: String,
    val recovery:Int,
    val price:Int,
    val display: Int,
    ) {

    fun heal(maxHP:Int):Int{
        return maxHP*recovery/100
    }
}

val smallPotion = Potion(
    "small potion",
    25,
    100,
    R.drawable.potion_health_1
)
val mediumPotion = Potion(
    "medium potion",
    50,
    1000,
    R.drawable.potion_health_2
)
val largePotion = Potion(
    "large potion",
    100,
    5000,
    R.drawable.potion_health_3
)