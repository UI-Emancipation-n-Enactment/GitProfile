package demo.gitprofiles.gitreposlist.presentation.view

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
sealed interface UiState {
    data object Empty : UiState
    data object LoadingState : UiState
    data class Success(val data: List<GithubReposDTO> = emptyList()) : UiState
    data class Error(val error: String) : UiState

}