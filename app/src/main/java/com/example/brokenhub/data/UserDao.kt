package com.example.brokenhub.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    fun getUser(email: String, password: String): User?

    @Insert
    fun insert(user: User)
}


