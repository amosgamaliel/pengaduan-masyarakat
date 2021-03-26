package com.rectangle.cepuonline.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rectangle.cepuonline.data.network.model.CURRENT_USER_ID
import com.rectangle.cepuonline.data.network.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user : User): Long

    @Query("SELECT * from user where uid = $CURRENT_USER_ID")
    fun getUser():LiveData<User>
}