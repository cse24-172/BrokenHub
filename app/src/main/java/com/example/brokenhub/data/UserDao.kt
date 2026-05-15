package com.example.brokenhub.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUser(uid: String): User?

    @Insert
    fun insertUser(user: User)
}





