package com.example.notes.ui.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
data class NoteEditRoute(val noteId: Long = -1L)