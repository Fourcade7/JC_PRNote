package com.pr7.jc_prnote.data.local.room

import android.app.Application
import androidx.room.Room
import com.m17.myapplication.data.room.AppDatabase


lateinit var  db: AppDatabase

class MyApp constructor():Application(){

    override fun onCreate() {
        super.onCreate()
        CONTEXT=this


        db= Room.databaseBuilder(
            CONTEXT,
            AppDatabase::class.java, "note0001"
        ).allowMainThreadQueries().build()
        //db.clearAllTables()

    }
}