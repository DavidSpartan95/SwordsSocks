package com.example.swordssocks.gladiator_items

import com.example.swordssocks.R
import kotlin.random.Random

class Weapon(
    val name:String,
    val power:Int,
    val acc:Int,
    val element:String,
    val criticalChance: Int,
    val display: Int,
    val equipped: Int,
    val price:Int
    ) {

    fun attack(level: Int, attack: Int, defense: Int, critChance: Int): Int {
        val isHit = Random.nextInt(101) <= acc
        if (isHit) {
            val isCrit = Random.nextInt(101) <= critChance
            val criticalMultiplier = if (isCrit) 2 else 1
            val randomMultiplier = Random.nextDouble(217.0, 256.0) / 255
            val baseDamage =
                ((2 * level * criticalMultiplier / 5 + 2) * power * attack / defense) / 50 + 2
            return (baseDamage * randomMultiplier).toInt()
        }
        return 0
    }
    fun magicAttack(level: Int, magic: Int, foeMagic: Int, critChance: Int): Int {
        val isHit = Random.nextInt(101) <= acc
        if (isHit) {
            val isCrit = Random.nextInt(101) <= critChance
            val criticalMultiplier = if (isCrit) 2 else 1
            val randomMultiplier = Random.nextDouble(217.0, 256.0) / 255
            val baseDamage =
                ((2 * level * criticalMultiplier / 5 + 2) * power * magic / foeMagic) / 50 + 2
            return (baseDamage * randomMultiplier).toInt()
        }
        return 0
    }
}

//MeleeWeapon
val woodSword = Weapon(
    "Wood Sword",
    20,
    95,
    "normal",
    12,
    R.drawable.sword_wood_display,
    R.drawable.sword_wood_equipped,
    100
)
val ironSword = Weapon(
    "Iron Sword",
    30,
    95,
    "normal",
    12,
    R.drawable.sword_iron_display,
    R.drawable.sword_iron_equipped,
    500
)
val goldSword = Weapon(
    "Gold Sword",
    40,
    95,
    "normal",
    12,
    R.drawable.sword_gold_display,
    R.drawable.sword_gold_equipped,
    2000
)
val diamondSword = Weapon(
    "Diamond Sword",
    50,
    95,
    "normal",
    12,
    R.drawable.sword_diamond_display,
    R.drawable.sword_diamond_equipped,
    10000
)

val woodAxe = Weapon(
    "Wood Axe",
    20,
    85,
    "normal",
    24,
    R.drawable.axe_wood_display,
    R.drawable.axe_wood_equipped,
    150
)
val ironAxe = Weapon(
    "Iron Axe",
    30,
    85,
    "normal",
    24,
    R.drawable.axe_iron_display,
    R.drawable.axe_iron_equipped,
    750
)
val goldAxe = Weapon(
    "Gold Axe",
    30,
    85,
    "normal",
    24,
    R.drawable.axe_gold_display,
    R.drawable.axe_gold_equipped,
    10750
)

val diamondAxe = Weapon(
    "Diamond Axe",
    30,
    85,
    "normal",
    24,
    R.drawable.axe_diamond_display,
    R.drawable.axe_diamond_equipped,
    10750
)
//Staff
val staffAmber = Weapon(
    "Amber Staff",
    15,
    100,
    "fire",
    12,
    R.drawable.staff_amber_display,
    R.drawable.staff_amber_equipped,
    200
)
val staffEmerald = Weapon(
    "Emerald Staff",
    25,
    100,
    "grass",
    12,
    R.drawable.staff_emerald_display,
    R.drawable.staff_emerald_equipped,
    500
)
val staffGarnet = Weapon(
    "Garnet Staff",
    50,
    100,
    "fire",
    12,
    R.drawable.staff_garnet_display,
    R.drawable.staff_garnet_equipped,
    1000
)
val staffDiamond = Weapon(
    "Amber Staff",
    70,
    100,
    "spark",
    12,
    R.drawable.staff_diamond_display,
    R.drawable.staff_diamond_equipped,
    5000
)


