package com.example.swordssocks.gladiator_items

class Potion(val name: String,val recovery:Int, val price:Int) {

    fun heal(maxHP:Int):Int{
        return maxHP*recovery/100
    }
}

val smallPotion = Potion("small potion", 25,100)
val mediumPotion = Potion("medium potion", 50,1000)
val largePotion = Potion("large potion", 100,5000)