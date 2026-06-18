package com.example.notes.ui.noteedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.local.NoteEntity
import com.example.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long = savedStateHandle.get<Long>("noteId") ?: -1L

    val isEditing: Boolean get() = noteId != -1L

    var title by mutableStateOf("")
        private set
    var content by mutableStateOf("")
        private set

    init {
        if (isEditing) {
            viewModelScope.launch {
                repository.getNoteById(noteId)?.let { note ->
                    title = note.title
                    content = note.content
                }
            }
        }
    }

    fun onTitleChange(value: String) { title = value }
    fun onContentChange(value: String) { content = value }

    fun save(onSaved: () -> Unit) {
        if (title.isBlank()) return

        viewModelScope.launch {
            if (isEditing) {
                val existingNote = repository.getNoteById(noteId)
                if (existingNote != null) {
                    repository.updateNote(
                        existingNote.copy(
                            title = title,
                            content = content,
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                }
            } else {
                repository.insertNote(
                    NoteEntity(
                        title = title,
                        content = content,
                        isPinned = false
                    )
                )
            }

            onSaved()
        }
    }
}