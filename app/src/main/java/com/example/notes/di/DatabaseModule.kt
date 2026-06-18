package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.data.local.NoteDao
import com.example.notes.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        // C'est ici que l'on construit réellement la base de données Room
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "notes_database" // C'est le nom du fichier physique qui sera créé sur le téléphone
        ).build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        // On récupère le DAO directement depuis l'instance de la base de données
        return database.noteDao()
    }
}