package com.davidspartangame.swordssocks.database

import androidx.compose.ui.graphics.Color
import com.davidspartangame.swordssocks.gladiator_items.smallPotion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun addUser(userRepository: UserRepository, user:User){
    userRepository.performDatabaseOperation(Dispatchers.IO){
        userRepository.addUser(user)
    }
}
suspend fun retrieveAllUsers(userRepository: UserRepository): List<User> = withContext(Dispatchers.IO) {
    // Perform the database operation within the IO Dispatcher
    userRepository.listAllUsers()
}

suspend fun getUserByID(userRepository: UserRepository,ID:Long?): User = withContext(Dispatchers.IO) {
    // Perform the database operation within the IO Dispatcher
    userRepository.getById(ID)
}