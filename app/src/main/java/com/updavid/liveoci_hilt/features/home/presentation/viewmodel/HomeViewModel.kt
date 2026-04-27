package com.updavid.liveoci_hilt.features.home.presentation.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.bored.domain.usecases.BoredActivitiesUseCases
import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification
import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotificationSource
import com.updavid.liveoci_hilt.features.home.domain.usecase.HomeUseCases
import com.updavid.liveoci_hilt.features.home.presentation.page.HomeUiState
import com.updavid.liveoci_hilt.features.user.domain.usescases.photo.PhotoUseCases
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val boredActivitiesUseCases: BoredActivitiesUseCases,
    private val userUseCases: UserUseCases,
    private val photoUseCases: PhotoUseCases,
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var loadedNotificationsUserId: String? = null

    init {
        fetchRemoteData()
        observeLocalData()
        calculateGreeting()
        fetchRecommendedActivity()
    }

    private fun fetchRemoteData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            val userDeferred = async { userUseCases.getUserByIdRemoteUseCase() }
            val photoDeferred = async { photoUseCases.getPhoto() }

            val userResult = userDeferred.await()
            val photoResult = photoDeferred.await()

            userResult.onFailure { error ->
                _uiState.update { it.copy(isError = error.message) }
            }

            if (photoResult.isFailure) {
                Log.e("HomeViewModel", "No se pudo traer la foto remotamente.")
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun calculateGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val (message, icon) = when (hour) {
            in 6..11 -> "Buenos días," to Icons.Default.WbSunny
            in 12..18 -> "Buenas tardes," to Icons.Default.WbTwilight
            else -> "Buenas noches," to Icons.Default.NightsStay
        }

        _uiState.update {
            it.copy(
                greeting = message,
                greetingIcon = icon
            )
        }
    }

    private fun observeLocalData() {
        viewModelScope.launch {
            userUseCases.getUserRoom().collect { user ->
                val userId = user?.id

                _uiState.update {
                    it.copy(
                        userId = userId,
                        userName = user?.name
                    )
                }

                if (!userId.isNullOrBlank() && loadedNotificationsUserId != userId) {
                    loadedNotificationsUserId = userId
                    loadNotifications(userId)
                }
            }
        }
    }

    fun fetchRecommendedActivity() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }

            val result = boredActivitiesUseCases.getRandomActivity()

            result.onSuccess { activity ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recommendedActivity = activity
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = error.message
                    )
                }
            }
        }
    }

    private fun loadNotifications(userId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoadingNotifications = true,
                    notificationError = null
                )
            }

            try {
                val notifications = homeUseCases.getHomeNotificationsUseCase(userId)

                _uiState.update {
                    it.copy(
                        notifications = notifications,
                        isLoadingNotifications = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingNotifications = false,
                        notificationError = e.message ?: "Error al cargar notificaciones"
                    )
                }
            }
        }
    }

    fun markNotificationRead(notification: HomeNotification) {
        viewModelScope.launch {
            try {
                if (notification.source == HomeNotificationSource.HTTP) {
                    homeUseCases.markHomeNotificationReadUseCase(notification.id)
                }

                _uiState.update { state ->
                    state.copy(
                        notifications = state.notifications.map { currentNotification ->
                            if (currentNotification.id == notification.id) {
                                currentNotification.copy(isRead = true)
                            } else {
                                currentNotification
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        notificationError = e.message ?: "Error al marcar notificación"
                    )
                }
            }
        }
    }

    fun markAllNotificationsRead() {
        val userId = _uiState.value.userId ?: return

        viewModelScope.launch {
            try {
                homeUseCases.markAllHomeNotificationsReadUseCase(userId)

                _uiState.update { state ->
                    state.copy(
                        notifications = state.notifications.map { notification ->
                            notification.copy(isRead = true)
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        notificationError = e.message ?: "Error al marcar todas las notificaciones"
                    )
                }
            }
        }
    }
}