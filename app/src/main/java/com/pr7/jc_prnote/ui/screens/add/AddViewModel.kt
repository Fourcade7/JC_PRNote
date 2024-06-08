package com.pr7.jc_prnote.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pr7.jc_prnote.data.local.room.MultiTask
import com.pr7.jc_prnote.data.local.room.Note
import com.pr7.jc_prnote.data.local.room.RoomInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel constructor():ViewModel() {

    private val noteDao=RoomInstance.noteDao
    private val multiTaskDao=RoomInstance.multiTaskDao

    //var allNotesList:List<Note> by mutableStateOf(listOf())

    fun addNote(note: Note)=viewModelScope.launch{
        noteDao.insertNote(note=note)
    }

    fun addMultiTask(
        category:String,
        key:String,
        list: List<String>)=viewModelScope.launch{

        list.forEach {
            multiTaskDao.insertMultiTask(
                multiTask = MultiTask(
                    title = it,
                    category = category,
                    key = key
                )
            )
        }

    }

//    fun getAllNote()=viewModelScope.launch(Dispatchers.IO) {
//        noteDao.getAllNotes().collect{
//            //allNotesList=it
//        }
//    }

//    fun updateNote(note: Note)=viewModelScope.launch{
//        noteDao.updateNote(note)
//    }
//
//    fun deleteNote(note: Note)=viewModelScope.launch{
//        noteDao.deleteNote(note)
//    }
//    fun deleteAllNote()=viewModelScope.launch{
//        noteDao.deleteAllNotes()
//    }


}