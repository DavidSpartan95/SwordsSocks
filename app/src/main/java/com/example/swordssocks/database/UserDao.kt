package com.example.swordssocks.database

import androidx.room.*
import com.example.swordssocks.gladiator_items.Armor
import com.example.swordssocks.gladiator_items.Potion
import com.example.swordssocks.gladiator_items.Weapon

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
    fun toggleWeapon(weapon:Weapon, id:Long?, order:String){
        val user = getById(id)

        when(order){
            "add"->{
                user.inventory.meleeWeapons.add(0, weapon)
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
    @Transaction
    fun toggleArmor(armor: Armor, id:Long?, order:String){
        val user = getById(id)

        when(order){
            "add"->{
                user.inventory.armors.add(0,armor)
                updateExistingUser(user)
            }
            "remove"->{
                for ((index,x) in user.inventory.armors.withIndex()){
                    if(x.name == armor.name ){
                        user.inventory.armors.removeAt(index)
                        break
                    }
                }
            }
        }
        updateExistingUser(user)
    }
    @Transaction
    fun togglePotion(potion: Potion, id:Long?, order:String){
        val user = getById(id)

        when(order){
            "add"->{
                user.inventory.potions += potion
                updateExistingUser(user)
            }
            "remove"->{
                for ((index,x) in user.inventory.potions.withIndex()){
                    if(x.name == potion.name ){
                        user.inventory.potions.removeAt(index)
                        break
                    }
                }
            }
        }
        updateExistingUser(user)
    }
    @Transaction
    fun toggleCoins(coins:Int, id:Long?){
        val user = getById(id)
        user.coins += coins
        updateExistingUser(user)
    }
    @Transaction
    fun buyItem(item:Inventory, user: User){
        val user = getById(user.id)
        var cost = 0
        if (item.meleeWeapons.isNotEmpty()){
            for (x in item.meleeWeapons){
                cost += x.price
                toggleWeapon(weapon = x, user.id,"add")
            }
        }
        if (item.potions.isNotEmpty()){
            for (x in item.potions){
                cost += x.price
                togglePotion(x,user.id,"add")
            }
        }
        if (item.armors.isNotEmpty()){
            for (x in item.armors){
                cost += x.price
                toggleArmor(x,user.id,"add")
            }
        }
        toggleCoins(-cost,user.id)

    }
}