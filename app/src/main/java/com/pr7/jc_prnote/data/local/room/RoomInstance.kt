package com.pr7.jc_prnote.data.local.room




object RoomInstance {

    var noteDao: NoteDao=db.noteDao()
    var multiTaskDao: MultiTaskDao=db.multiTaskDao()

}