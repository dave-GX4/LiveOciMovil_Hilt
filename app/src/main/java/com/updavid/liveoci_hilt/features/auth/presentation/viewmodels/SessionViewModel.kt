package com.updavid.liveoci_hilt.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.core.navigation.Home
import com.updavid.liveoci_hilt.core.navigation.Login
import com.updavid.liveoci_hilt.features.auth.domain.usecases.CheckSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase
) : ViewModel() {

    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoggedIn = checkSessionUseCase()
            _startDestination.value = if (isLoggedIn) Home else Login
        }
    }
}