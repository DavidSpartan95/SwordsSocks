package com.example.swordssocks.gladiator_items

class Potion(val name: String,val recovery:Int) {

    fun heal(maxHP:Int):Int{
        return maxHP*recovery/100
    }
}

val smallPotion = Potion("small potion", 25)
val mediumPotion = Potion("medium potion", 50)
val largePotion = Potion("large potion", 100)