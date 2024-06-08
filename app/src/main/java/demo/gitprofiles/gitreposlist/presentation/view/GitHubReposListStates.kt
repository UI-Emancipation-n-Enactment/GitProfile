package demo.gitprofiles.gitreposlist.presentation.view

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
sealed interface RepoState {
    data class Empty(val nwException: String = "") : RepoState
    data class Loading(val isLoading: Boolean) : RepoState
    data class Success(val data: List<GithubReposDTO> = emptyList()) : RepoState
    data class Error(val error: String) : RepoState

}