package com.example.swordssocks.database

import com.example.swordssocks.gladiator_items.MeleeWeapon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.ByteOrder

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
    fun toggleMeleeWeapon(newWeapon: MeleeWeapon, id:Long?,order:String){
        appDatabase.userDao().toggleMeleeWeapon(newWeapon,id,order)
    }
    fun getById(id:Long?):User{
        return appDatabase.userDao().getById(id)
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
