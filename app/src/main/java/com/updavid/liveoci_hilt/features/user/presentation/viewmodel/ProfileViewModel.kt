package com.updavid.liveoci_hilt.features.user.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.updavid.liveoci_hilt.features.user.domain.usescases.photo.PhotoUseCases
import com.updavid.liveoci_hilt.features.user.domain.usescases.user.UserUseCases
import com.updavid.liveoci_hilt.features.user.presentation.page.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val photoUseCases: PhotoUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeLocalData()
    }

    private fun observeLocalData() {
        viewModelScope.launch {
            userUseCases.getUserRoom().collect { user ->
                _uiState.update { it.copy(user = user) }
            }
        }
    }

    fun refreshUserRemote() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefresh = true, isError = null) }

            val userDeferred = async { userUseCases.getUserByIdRemoteUseCase() }
            val photoDeferred = async { photoUseCases.getPhoto() }

            val userResult = userDeferred.await()
            val photoResult = photoDeferred.await()

            userResult.onFailure { error ->
                _uiState.update { it.copy(isError = error.message) }
            }

            photoResult.onSuccess { photo ->
                val freshUrl = "${photo.url}?t=${System.currentTimeMillis()}"
                _uiState.update { it.copy(userPhotoUrl = freshUrl) }
            }.onFailure {
                Log.e("ProfileViewModel", "No se pudo actualizar la foto remotamente.")
            }

            _uiState.update { it.copy(isRefresh = false) }
        }
    }

    fun uploadProfilePhoto(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isError = null) }
            try {
                val file = getFileFromUri(context, imageUri)

                if (file == null) {
                    _uiState.update { it.copy(isLoading = false, isError = "No se pudo procesar la imagen.") }
                    return@launch
                }

                photoUseCases.savePhoto(file).onSuccess {
                    val photoResult = photoUseCases.getPhoto()

                    photoResult.onSuccess { photo ->
                        val freshUrl = "${photo.url}?t=${System.currentTimeMillis()}"
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = "Foto actualizada",
                                userPhotoUrl = freshUrl
                            )
                        }
                    }.onFailure { error ->
                        _uiState.update { it.copy(isLoading = false, isError = "Foto subida, pero error al recargar: ${error.message}") }
                    }
                }.onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, isError = error.message) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, isError = "Error al procesar la imagen.") }
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("profile_img", ".jpg", context.cacheDir)
            val outputStream = FileOutputStream(tempFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            userUseCases.logout().onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = "Sesión cerrada") }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, isError = error.message) }
            }
        }
    }

    fun onNotificationToggled(enabled: Boolean) {
        _uiState.update { it.copy(notificationStatus = enabled) }
    }

    fun onDarkModeToggled(enabled: Boolean) {
        _uiState.update { it.copy(darkModeStatus = enabled) }
    }
}