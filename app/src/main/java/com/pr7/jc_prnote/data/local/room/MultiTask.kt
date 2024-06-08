package com.pr7.jc_prnote.data.local.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize()
@Entity(tableName = "MultiTask")
class MultiTask constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val uid:Int=0,
    @ColumnInfo(name = "title")
    val title:String?=null,
    @ColumnInfo(name = "category")
    val category:String?=null,
    @ColumnInfo(name = "key")
    val key:String?=null,
    @ColumnInfo(name = "status")
    val status:Boolean=false,
): Parcelable