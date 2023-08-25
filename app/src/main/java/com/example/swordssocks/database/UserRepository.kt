package com.example.swordssocks.database

import com.example.swordssocks.gladiator_items.Weapon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserRepository(
    private val appDatabase: AppDatabase,
    private val coroutineScope: CoroutineScope
) {

    fun addUser(user: User) {
        appDatabase.userDao().insertUser(user)
    }
    fun listAllUsers():List<User>{
        return appDatabase.userDao().getAllUsers()
    }
    fun toggleWeapon(newWeapon: Weapon, id:Long?, order:String){
        appDatabase.userDao().toggleWeapon(newWeapon,id,order)
    }
    fun getById(id:Long?):User{
        return appDatabase.userDao().getById(id)
    }
    fun buyItem(item:Inventory, user: User){
        appDatabase.userDao().buyItem(item,user)
    }
    fun toggleCoins(coins:Int, id: Long?){
        appDatabase.userDao().toggleCoins(coins, id)
    }
    fun levelUp(exp:Int, id: Long?):Pair<Boolean,Int>{
        return appDatabase.userDao().levelUp(exp, id)
    }
    fun newStats(id:Long?,HP:Int,atk:Int,def:Int,cha:Int,magic:Int,){
        appDatabase.userDao().newStats(id,HP,atk,def,cha,magic)
    }
    fun performDatabaseOperation(
        dispatcher: CoroutineDispatcher,
        databaseOperation: suspend () -> Unit
    ) {
        coroutineScope.launch(dispatcher) {
            databaseOperation()
        }
    }
}
