package com.pr7.jc_prnote.ui.screens.home

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

class HomeViewModel constructor():ViewModel() {

    private val noteDao=RoomInstance.noteDao
    private val multiTaskDao=RoomInstance.multiTaskDao

    var allNotesList:List<Note> by mutableStateOf(listOf())
    var allMultiTaskList:List<MultiTask> by mutableStateOf(listOf())

    init {
        getAllNote()
        getAllMultiTask()

    }

    fun getAllNote()=viewModelScope.launch{
        noteDao.getAllNotes().collect{
            allNotesList=it
        }
    }

    fun getAllMultiTask()=viewModelScope.launch{
        multiTaskDao.getAllMultiTasks().collect{
            allMultiTaskList=it
        }
    }
    fun addNote(note: Note)=viewModelScope.launch{
        noteDao.insertNote(note)
    }
    fun updateNote(note: Note)=viewModelScope.launch{
        noteDao.updateNote(note)
    }

    fun deleteNote(note: Note)=viewModelScope.launch{
        noteDao.deleteNote(note)
    }
    fun deleteAllNote()=viewModelScope.launch{
        noteDao.deleteAllNotes()
    }


}