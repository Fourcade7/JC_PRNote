package com.pr7.jc_prnote.data.local.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize()
@Entity(tableName = "Note")
class Note constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val uid:Int=0,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "dataTime")
    val dataTime:String,
    @ColumnInfo(name = "dataTime2")
    val dataTime2:String,
    @ColumnInfo(name = "priority")
    val priority:String,
    @ColumnInfo(name = "category")
    val category:String,
    @ColumnInfo(name = "status")
    val status:Boolean=false,
): Parcelable



