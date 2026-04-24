import androidx.lifecycle.ViewModel
import com.updavid.liveoci_hilt.features.code.presentation.viewmodels.CodeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class CodeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CodeUiState())
    val uiState: StateFlow<CodeUiState> = _uiState.asStateFlow()

    fun toggleInvitationCodeVisibility() {
        _uiState.value = _uiState.value.copy(
            isInvitationCodeVisible = !_uiState.value.isInvitationCodeVisible
        )
    }
}