package demo.gitprofiles.repos.presentation

import demo.gitprofiles.repos.data.network.response.GithubReposListDTO

sealed class UIState {
    data object EmptyState : UIState()
    class SuccessState(val githubReposListDTO: GithubReposListDTO?) : UIState()
    class ErrorState(val error: String) : UIState() {
        fun displayError(error: String) {
            println("This is an error: $error")
        }
    }
    class LoadingState(val isLoadingState: Boolean) : UIState()
}
