package com.davidspartangame.swordssocks.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.davidspartangame.swordssocks.gladiator_items.*

@Entity
data class User(
    @TypeConverters(ObjectConverter::class,DrawConverter::class)
    var name:String,
    var health: Int,
    var strength: Int,
    var charisma: Int,
    var defence: Int,
    var magic: Int,
    var coins: Int,
    var exp: Int,
    var level: Int,
    var draw: DrawInstruction,
    var inventory: Inventory

) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

