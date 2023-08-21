package com.example.swordssocks.gladiator_items

import com.example.swordssocks.R

class Armor(
    val name:String ,
    val armor:Int,
    val part: String,
    val equipped: Int,
    val display: Int,
    val price: Int,
) {

}
//Sandals
val basicSandal = Armor(
    name = "Basic Sandal",
    armor = 5, "Sandal",
    R.drawable.character_sandals_1,
    R.drawable.character_sandals_1,
    100
)
val normalSandal = Armor(
    name = "Normal Sandal",
    armor = 15, "Sandal",
    R.drawable.character_sandals_2,
    R.drawable.character_sandals_2,
    250
)
val clogs = Armor(
    name = "Clogs",
    armor = 50,
    "Sandal",
    R.drawable.character_sandals_3,
    R.drawable.character_sandals_3,
    1500
)
//Socks
val basicSocks = Armor(
    name = "Basic Socks",
    armor = 5, "Socks",
    R.drawable.character_socks_1,
    R.drawable.character_socks_1,
    100
)
val normalSocks  = Armor(
    name = "Normal Socks",
    armor = 15, "Socks",
    R.drawable.character_socks_2,
    R.drawable.character_socks_2,
    250
)
val greatSocks = Armor(
    name = "Great Socks",
    armor = 50, "Socks",
    R.drawable.character_socks_3,
    R.drawable.character_socks_3,
    1500
)
//chestplate
val ironChestplate = Armor(
    name = "Iron Chestplate",
    armor = 25,
    "Chest",
    R.drawable.character_chestplate_2,
    R.drawable.character_chestplate_2_display,
    1000
)
val goldChestplate = Armor(
    name = "Gold Chestplate",
    armor = 50,
    "Chest",
    R.drawable.character_chestplate_3,
    R.drawable.character_chestplate_3_display,
    5000
)
//Helmets
val leatherHelmet = Armor(
    name = "Leather Helmet",
    armor = 7,
    "Helmet",
    R.drawable.character_helmet_1,
    R.drawable.character_helmet_1_display,
    200
)
val ironHelmet = Armor(
    name = "Iron Helmet",
    armor = 14,
    "Helmet",
    R.drawable.character_helmet_2,
    R.drawable.character_helmet_2_display,
    500,
)
val goldHelmet = Armor(
    name = "Gold Helmet",
    armor = 30,
    "Helmet",
    R.drawable.character_helmet_3,
    R.drawable.character_helmet_3_display,
    1500,
)
//leggins
val ironLeggings = Armor(
    name = "Iron Leggings",
    armor = 20,
    "Leggings",
    R.drawable.character_leggins_2,
    R.drawable.character_leggins_2_display,
    900,
)
val goldLeggings = Armor(
    name = "Gold Leggings",
    armor = 40,
    "Leggings",
    R.drawable.character_leggins_3,
    R.drawable.character_leggins_3_display,
    4000,
)
