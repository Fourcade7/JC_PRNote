package com.m17.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.MultiTaskDao
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.NoteDao


@Database(entities = [Note::class,MultiTask::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao():NoteDao
    abstract fun multiTaskDao():MultiTaskDao
}
