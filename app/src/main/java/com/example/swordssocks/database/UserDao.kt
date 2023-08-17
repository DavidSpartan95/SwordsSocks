package com.example.swordssocks.database

import androidx.room.*
import com.example.swordssocks.gladiator_items.MeleeWeapon

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)
    @Delete
    fun deleteUser(user: User)
    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateExistingUser(user: User)
    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id:Long?): User
    @Transaction
    fun toggleMeleeWeapon(weapon:MeleeWeapon, id:Long?,order:String){
        val user = getById(id)

        when(order){
            "add"->{
                user.inventory.meleeWeapons += weapon
                updateExistingUser(user)
            }
            "remove"->{
                for ((index,x) in user.inventory.meleeWeapons.withIndex()){
                    if(x.name == weapon.name ){
                        user.inventory.meleeWeapons.removeAt(index)
                        break
                    }
                }
            }
        }

        updateExistingUser(user)
    }
}