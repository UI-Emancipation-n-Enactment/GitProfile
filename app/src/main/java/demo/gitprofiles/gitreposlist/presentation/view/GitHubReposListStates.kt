package demo.gitprofiles.gitreposlist.presentation.view

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
sealed interface RepoState {
    data object Empty : RepoState
    data object LoadingState : RepoState
    data class Success(val data: List<GithubReposDTO> = emptyList()) : RepoState
    data class Error(val error: String) : RepoState

}