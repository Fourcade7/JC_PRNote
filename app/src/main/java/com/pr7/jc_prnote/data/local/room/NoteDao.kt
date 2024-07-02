package com.pr7.jc_prnote.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>> //Dao functions that have a suspend modifier must not return a deferred/async type (kotlinx.coroutines.flow.Flow). Most probably this is an error. Consider changing the return type or removing the suspend modifier.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Update
    suspend fun updateNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)
    @Query("DELETE FROM Note")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM Note WHERE id=:id ")
    fun getById(id: Int): Flow<Note?>

//    @Query("DELETE FROM myTableName")
//    fun nukeTable()

}