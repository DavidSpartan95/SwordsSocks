package com.example.swordssocks.gladiator_items

import com.example.swordssocks.R

//part = for example  "boots"
class Armor(
    val name:String ,
    val armor:Int,
    val part: String,
    val display: Int,
    val price: Int,
) {

}
//Sandals
val basicSandal = Armor(name = "Basic Sandal", armor = 5, "Sandal", R.drawable.character_sandals_1, 100 )
val normalSandal = Armor(name = "Normal Sandal", armor = 15, "Sandal", R.drawable.character_sandals_2, 250 )
val clogs = Armor(name = "Clogs", armor = 50, "Sandal", R.drawable.character_sandals_3, 1500 )
//Socls

val basicSocks = Armor(name = "Basic Socks", armor = 5, "Socks", R.drawable.character_socks_1, 100 )
val normalSocks  = Armor(name = "Normal Socks", armor = 15, "Socks", R.drawable.character_socks_2, 250 )
val greatSocks = Armor(name = "Great Socks", armor = 50, "Socks", R.drawable.character_socks_3, 1500 )
//