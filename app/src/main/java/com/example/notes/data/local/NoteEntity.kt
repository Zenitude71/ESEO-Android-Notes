package com.example.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Initialisé à 0 pour laisser Room gérer l'auto-génération
    val title: String,
    val content: String,
    val isPinned: Boolean,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),

    // Ajout d'un champ personnalisé (catégorie) suite à l'encart "À vous de jouer"
    val category: String = "Personnel"
)