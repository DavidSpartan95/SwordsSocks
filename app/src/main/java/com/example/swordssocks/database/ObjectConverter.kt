package com.example.swordssocks.database

import androidx.room.TypeConverter
import com.example.swordssocks.gladiator_items.Armor
import com.example.swordssocks.gladiator_items.Weapon
import com.example.swordssocks.gladiator_items.Potion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.ui.graphics.Color

class ObjectConverter {
    private val gson = Gson()

    @TypeConverter
    fun inventoryToString(inventory: Inventory): String {
        return gson.toJson(inventory)
    }

    @TypeConverter
    fun stringToInventory(inventoryString: String): Inventory {
        val listType = object : TypeToken<Inventory>() {}.type
        return gson.fromJson(inventoryString, listType)
    }

}
class DrawConverter{
    private val gson = Gson()
    @TypeConverter
    fun drawInstructionToString(drawInstruction: DrawInstruction ): String{
        return gson.toJson(drawInstruction)
    }
    @TypeConverter
    fun stringToDrawInstruction(drawInstruction: String):DrawInstruction{
        val listType = object : TypeToken<DrawInstruction>() {}.type
        return gson.fromJson(drawInstruction, listType)
    }
}

class Inventory (
    var potions: ArrayList<Potion>,
    var meleeWeapons: ArrayList<Weapon>,
    var armors: ArrayList<Armor>
){
}

class DrawInstruction(
    var hair: Int,
    var hairColor: Color,
    var eyes:Int,
    var mouth: Int,
    var skin: Int,
){

}