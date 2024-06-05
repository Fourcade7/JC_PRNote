package com.pr7.jc_prnote.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<Note>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)
    @Update
    fun updateNote(note: Note)
    @Delete
    fun deleteNote(note: Note)
    @Query("DELETE FROM Note")
    fun deleteAllNotes()

}