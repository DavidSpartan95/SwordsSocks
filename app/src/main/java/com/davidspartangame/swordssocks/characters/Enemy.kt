package com.davidspartangame.swordssocks.characters

import androidx.compose.ui.graphics.Color
import com.davidspartangame.swordssocks.R
import com.davidspartangame.swordssocks.database.DrawInstruction
import com.davidspartangame.swordssocks.database.Inventory
import com.davidspartangame.swordssocks.database.User
import com.davidspartangame.swordssocks.game_components.shop.armorShop
import com.davidspartangame.swordssocks.game_components.shop.weaponArray
import com.davidspartangame.swordssocks.gladiator_items.*
import kotlin.random.Random


fun generateFoe(userLevel: Int):User{
    return when(userLevel){
        10 -> com.davidspartangame.swordssocks.characters.level10Boss(userLevel)
        else -> com.davidspartangame.swordssocks.characters.generateRandomFoe(userLevel)
    }
}
fun generateRandomFoe(userLevel: Int):User{
    val stats = com.davidspartangame.swordssocks.characters.randomizeStats(14 + (12 * userLevel))
    return User(
        name = "Angry Man",
        health = stats[0],
        strength = stats[1],
        charisma = stats[2],
        defence = stats[3],
        magic = stats[4],
        coins = (395*userLevel/7),
        exp = (64*userLevel/7),
        level = userLevel,
        draw = com.davidspartangame.swordssocks.characters.randomDrawInstructions(),
        inventory = com.davidspartangame.swordssocks.characters.randomizeInventory(userLevel)
    )
}
fun level10Boss(userLevel: Int):User{
    return User(
        name = "Zombie",
        health = 25,
        strength = 45,
        charisma = 5,
        defence = 25,
        magic = 25,
        coins = (395*userLevel/7),
        exp = (330),
        level = userLevel,
        draw = DrawInstruction(
            R.drawable.boss_1,
            Color.White,
            R.drawable.boss_1,
            R.drawable.boss_1,
            R.drawable.boss_1,
        ),
        inventory = Inventory(
            arrayListOf(),
            arrayListOf(Weapon("spear",30,90,"normal",15,R.drawable.boss_1,R.drawable.boss_1,0)),
            arrayListOf(Armor("Undead Armor",50,"Sandal",R.drawable.boss_1,R.drawable.boss_1,0))
        )
    )
}


fun randomizeStats(skillPoints:Int):Array<Int>{
    var points = skillPoints
    var stats = arrayOf(1,1,1,1,1)
    while (points > 0){
        val randomNum = Random.nextInt(5)
        var statsCopy = stats.copyOf()
        statsCopy[randomNum] = statsCopy[randomNum]+1
        stats = statsCopy
        points--
    }
    return stats
}
fun randomDrawInstructions():DrawInstruction{

    return DrawInstruction(
        com.davidspartangame.swordssocks.characters.hairStyles[Random.nextInt(com.davidspartangame.swordssocks.characters.hairStyles.size)],
        com.davidspartangame.swordssocks.characters.colors[Random.nextInt(com.davidspartangame.swordssocks.characters.colors.size)],
        com.davidspartangame.swordssocks.characters.eyes[Random.nextInt(com.davidspartangame.swordssocks.characters.eyes.size)],
        com.davidspartangame.swordssocks.characters.mouths[Random.nextInt(com.davidspartangame.swordssocks.characters.mouths.size)],
        com.davidspartangame.swordssocks.characters.skins[Random.nextInt(com.davidspartangame.swordssocks.characters.skins.size)]
    )
}

fun randomizeInventory(level: Int):Inventory{
    var coins = 100
    var levelLoop = 1
    var exp = 0
    while (levelLoop < level){
        coins += (395*levelLoop/7)
        exp += (64*levelLoop/7)
        while (true){
            if (exp >= ((levelLoop+1)*(levelLoop+1)*(levelLoop+1))){
                levelLoop++
            }else{
                break
            }
        }
    }

    var randomWeaponArray:ArrayList<Weapon> = arrayListOf()
    while (true){
        val i = Random.nextInt(weaponArray.size)
        if (weaponArray[i].price <= coins){
            coins -=weaponArray[i].price
            randomWeaponArray += weaponArray[i]
            break
        }
    }
    var randomArmorArray:ArrayList<Armor> = arrayListOf()
    while (coins >= 100){
        val i = Random.nextInt(armorShop.size)
        if (armorShop[i].price <= coins){
            coins -= armorShop[i].price
            randomArmorArray += armorShop[i]
        }
    }

    return Inventory(arrayListOf(), randomWeaponArray,randomArmorArray)
}