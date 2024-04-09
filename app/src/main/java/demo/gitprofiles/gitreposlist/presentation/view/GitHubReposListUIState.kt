package demo.gitprofiles.gitreposlist.presentation.view

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO

data class GitHubReposListUIState(
    val isLoading: Boolean = false,
    val githubApiCallList: List<GithubReposDTO> = emptyList()
)
