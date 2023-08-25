package com.example.swordssocks.characters

import com.example.swordssocks.database.DrawInstruction
import com.example.swordssocks.database.Inventory
import com.example.swordssocks.database.User
import com.example.swordssocks.gladiator_items.woodSword
import kotlin.random.Random

fun generateFoe(userLevel: Int):User{
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
        inventory = Inventory(arrayListOf(), arrayListOf(woodSword),arrayListOf())
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