package com.example.swordssocks.characters

import androidx.compose.ui.graphics.Color
import com.example.swordssocks.R
import com.example.swordssocks.database.DrawInstruction
import com.example.swordssocks.database.Inventory
import com.example.swordssocks.database.User
import com.example.swordssocks.game_components.shop.armorShop
import com.example.swordssocks.game_components.shop.weaponArray
import com.example.swordssocks.gladiator_items.*
import kotlin.random.Random


fun generateFoe(userLevel: Int):User{
    return when(userLevel){
        10 -> level10Boss(userLevel)
        else -> generateRandomFoe(userLevel)
    }
}
fun generateRandomFoe(userLevel: Int):User{
    val stats = randomizeStats(14+(12*userLevel))
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
        draw = randomDrawInstructions(),
        inventory = randomizeInventory(userLevel)
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
        hairStyles[Random.nextInt(hairStyles.size)],
        colors[Random.nextInt(colors.size)],
        eyes[Random.nextInt(eyes.size)],
        mouths[Random.nextInt(mouths.size)],
        skins[Random.nextInt(skins.size)]
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