package com.example.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    // Fournit l'accès au DAO
    abstract fun noteDao(): NoteDao

}