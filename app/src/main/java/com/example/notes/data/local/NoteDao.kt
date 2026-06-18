package com.example.notes.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // Récupère toutes les notes, triées par statut "épinglé" puis par date de modification
    @Query("SELECT * FROM notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    // Récupère une note spécifique par son ID
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?

    // Insère une note (ou la remplace si l'ID existe déjà)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    // Met à jour une note existante
    @Update
    suspend fun updateNote(note: NoteEntity)

    // Supprime une note
    @Delete
    suspend fun deleteNote(note: NoteEntity)

    // BONUS (À vous de jouer) : Recherche par mot-clé dans le titre
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchNotes(query: String): Flow<List<NoteEntity>>
}