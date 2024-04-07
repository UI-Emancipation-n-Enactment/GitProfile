package demo.gitprofiles.repos.presentation.view

import demo.gitprofiles.repos.data.network.response.GithubReposDTO

data class GitHubReposListUIState(
    val isLoading: Boolean = false,
    val githubApiCallList: List<GithubReposDTO> = emptyList()
)
