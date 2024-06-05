package com.pr7.jc_prnote.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface MultiTaskDao {
    @Query("SELECT * FROM MultiTask")
    fun getAllMultiTasks(): LiveData<List<MultiTask>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMultiTask(multiTask: MultiTask)
    @Update
    fun updateMultiTask(multiTask: MultiTask)
    @Delete
    fun deleteMultiTask(multiTask: MultiTask)
    @Query("DELETE FROM MultiTask")
    fun deleteAllMultiTasks()

}