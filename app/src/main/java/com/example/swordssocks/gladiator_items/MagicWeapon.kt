package com.example.swordssocks.gladiator_items

import kotlin.random.Random

class MagicWeapon(
    val name:String,
    val power:Int,
    val acc:Int,
    val element:String,
    val CriticalChance: Int
) {
    fun attack(level: Int, magic: Int, foeMagic: Int, critChance: Int): Int {
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