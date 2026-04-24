import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val userName: String = "Antonio",
    val email: String = "anton343@gmail.com",
    val status: String = "pasivo",
    val invitationCode: String = "ANT-8K92-XP",
    val isInvitationCodeVisible: Boolean = false
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleInvitationCodeVisibility() {
        _uiState.value = _uiState.value.copy(
            isInvitationCodeVisible = !_uiState.value.isInvitationCodeVisible
        )
    }
}