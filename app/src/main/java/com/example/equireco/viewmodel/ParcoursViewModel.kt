package com.example.equireco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.equireco.data.Parcours
import com.example.equireco.data.ParcoursRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ParcoursViewModel(private val repository: ParcoursRepository) : ViewModel() {
    val allParcours: StateFlow<List<Parcours>> = repository.allParcours
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var editingParcours: Parcours? = null
        private set

    fun setEditingParcours(parcours: Parcours) {
        editingParcours = parcours
    }

    fun clearEditingParcours() {
        editingParcours = null
    }

    fun addParcours(nom: String, lieu: String, date: String) = viewModelScope.launch {
        repository.insert(Parcours(nom = nom, lieu = lieu, date = date))
    }

    fun updateParcours(nom: String, lieu: String, date: String) = viewModelScope.launch {
        editingParcours?.let {
            val updated = it.copy(nom = nom, lieu = lieu, date = date)
            repository.update(updated)
        }
    }

    fun deleteParcours(parcours: Parcours) = viewModelScope.launch {
        repository.delete(parcours)
    }

    companion object {
        fun Factory(repository: ParcoursRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ParcoursViewModel(repository) as T
                }
            }
        }
    }
}
