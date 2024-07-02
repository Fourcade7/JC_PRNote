package com.pr7.jc_prnote.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MultiTaskDao {
    @Query("SELECT * FROM MultiTask")
    fun getAllMultiTasks(): Flow<List<MultiTask>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiTask(multiTask: MultiTask)
    @Update
    suspend fun updateMultiTask(multiTask: MultiTask)
    @Delete
    suspend fun deleteMultiTask(multiTask: MultiTask)
    @Query("DELETE FROM MultiTask")
    suspend fun deleteAllMultiTasks()

    @Query("SELECT * FROM MultiTask WHERE id=:id ")
    fun getById(id: Int): Flow<MultiTask?>

}