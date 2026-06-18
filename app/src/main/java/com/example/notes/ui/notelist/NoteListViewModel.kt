package com.example.notes.ui.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.local.NoteEntity
import com.example.notes.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<NoteListUiState> = combine(
        repository.getAllNotes(),
        _errorMessage
    ) { notes, error ->
        NoteListUiState(
            notes = notes,
            isLoading = false,
            errorMessage = error
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = NoteListUiState(isLoading = true)
    )

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la suppression : ${e.localizedMessage}"
            }
        }
    }

    fun togglePin(note: NoteEntity) {
        viewModelScope.launch {
            try {
                val updatedNote = note.copy(isPinned = !note.isPinned)
                repository.updateNote(updatedNote)
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la modification de la note"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}