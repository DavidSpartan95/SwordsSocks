package com.example.swordssocks.characters

import kotlin.random.Random
fun containsOnlyAlphabets(input: String): Boolean {
    if (input.isEmpty()){
        return true
    }else{
        val regex = Regex("^[a-zA-Z0-9]+$")
        return input.matches(regex)
    }
}
fun randomNameGenerator():String{
    val randomIndex = Random.nextInt(50)
    return  maleNames[randomIndex]
}
val maleNames = arrayOf(
    "Aldric",
    "Bryce",
    "Cedric",
    "Dorian",
    "Edric",
    "Finnian",
    "Gareth",
    "Hadrian",
    "Isidore",
    "Jareth",
    "Kael",
    "Lorcan",
    "Mael",
    "Nikolai",
    "Oberon",
    "Percival",
    "Quintus",
    "Roland",
    "Sebastian",
    "Thaddeus",
    "Ulric",
    "Vance",
    "Wesley",
    "Xander",
    "Yorick",
    "Zephyr",
    "Alaric",
    "Balthazar",
    "Cassius",
    "Darius",
    "Emeric",
    "Felix",
    "Gideon",
    "Hector",
    "Ignatius",
    "Jasper",
    "Kieran",
    "Leopold",
    "Malachi",
    "Nathaniel",
    "Orlando",
    "Peregrine",
    "Quentin",
    "Roderick",
    "Seamus",
    "Theodore",
    "Uriah",
    "Valerian",
    "Wilhelm",
    "Xavier",
    "Yarrow",
    "Zephyrus"
)