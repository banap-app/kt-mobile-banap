package com.banap.banap.app.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.data.repository.navigation.NavigationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val repository: NavigationRepository
) : ViewModel() {
    val lastScreen: StateFlow<String> = repository.lastScreen
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            "EngineerHome"
        )

    fun recordRoute(route: String) {
        viewModelScope.launch {
            repository.saveLastScreen(route)
        }
    }
}