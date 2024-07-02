package com.pr7.jc_prnote.ui.screens.update

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

class UpdateViewModel constructor():ViewModel() {

    private val noteDao=RoomInstance.noteDao
    private val multiTaskDao=RoomInstance.multiTaskDao

    //var allNotesList:List<Note> by mutableStateOf(listOf())
    var allMultiTaskList:List<MultiTask> by mutableStateOf(listOf())

    var getByIdNote:Note? by mutableStateOf(Note())

    init {
        getAllMultiTask()
    }


    fun updateNote(note: Note)=viewModelScope.launch{
        noteDao.updateNote(note)
    }

    fun updateMultitask(multiTask: MultiTask)=viewModelScope.launch{
        multiTaskDao.updateMultiTask(multiTask)
    }

   fun deleteMultiTask(multiTask: MultiTask)=viewModelScope.launch{
        multiTaskDao.deleteMultiTask(multiTask = multiTask)
    }

    fun getAllMultiTask()=viewModelScope.launch{
        multiTaskDao.getAllMultiTasks().collect{
            allMultiTaskList=it
        }
    }
    fun getById(id:Int)=viewModelScope.launch{
        noteDao.getById(id).collect{
            getByIdNote=it
        }
    }

    fun addMultiTask(multiTask: MultiTask)=viewModelScope.launch{

            multiTaskDao.insertMultiTask(multiTask)

    }


//    fun addMultiTask(
//        category:String,
//        key:String,
//        list: List<String>)=viewModelScope.launch{
//
//        list.forEach {
//            multiTaskDao.insertMultiTask(
//                multiTask = MultiTask(
//                    title = it,
//                    category = category,
//                    key = key
//                )
//            )
//        }
//
//    }
//    fun addNote(note: Note)=viewModelScope.launch{
//        noteDao.insertNote(note=note)
//    }

//    fun getAllNote()=viewModelScope.launch(Dispatchers.IO) {
//        noteDao.getAllNotes().collect{
//            //allNotesList=it
//        }
//    }

//
//    fun deleteNote(note: Note)=viewModelScope.launch{
//        noteDao.deleteNote(note)
//    }
//    fun deleteAllNote()=viewModelScope.launch{
//        noteDao.deleteAllNotes()
//    }


}