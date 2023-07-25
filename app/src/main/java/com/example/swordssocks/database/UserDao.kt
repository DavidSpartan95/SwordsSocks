package com.example.swordssocks.database

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)
    @Delete
    fun deleteUser(user: User)
    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

}