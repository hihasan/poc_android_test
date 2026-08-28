package xyz.hihasan.ledgerlite.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.hihasan.ledgerlite.core.domain.usecase.DashboardData
import xyz.hihasan.ledgerlite.core.domain.usecase.GetDashboardUseCase
import javax.inject.Inject

sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Ready(val data: DashboardData) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getDashboard: GetDashboardUseCase,
) : ViewModel() {

    val state: StateFlow<DashboardUiState> = getDashboard()
        .map<DashboardData, DashboardUiState> { DashboardUiState.Ready(it) }
        .catch { emit(DashboardUiState.Error(it.message ?: "Failed to load dashboard")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DashboardUiState.Loading,
        )
}
