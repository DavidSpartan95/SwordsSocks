package com.example.swordssocks.characters

import com.example.swordssocks.database.DrawInstruction
import com.example.swordssocks.database.Inventory
import com.example.swordssocks.database.User
import com.example.swordssocks.gladiator_items.woodSword
import kotlin.random.Random

fun GenerateFoe():User{
    return User(
        name = "Angry Man",
        health = 1,
        strength = 5,
        charisma = 2,
        defence = 2,
        magic = 2,
        coins = 100,
        exp = 100,
        level = 1,
        draw = randomDrawInstructions(),
        inventory = Inventory(arrayListOf(), arrayListOf(woodSword),arrayListOf())
    )
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