package demo.gitprofiles.repos.presentation.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.repos.data.network.response.GithubReposListDTO
import demo.gitprofiles.repos.presentation.UIState
import demo.gitprofiles.repos.presentation.view.GitHubReposListUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class GithubReposListViewModel @Inject constructor(
    private val githubReposService: GithubReposService
) : ViewModel() {
    private val _reposApiCallUIState = MutableStateFlow(GitHubReposListUIState())
    val reposApiCallUIState: StateFlow<GitHubReposListUIState> = _reposApiCallUIState.asStateFlow()

    init {
        fetchGithubRepos()
    }

    private fun fetchGithubRepos() {
        Log.d("in-viewModel", "calling prepareGithubRepos")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("in-viewModel", " update-FLow-UIState in ${this.javaClass}")

                flow<UIState<GithubReposListDTO>> {
                    val gitReposFromApi: GithubReposListDTO? = try {
                        githubReposService.getReposList("snaqviApps")
                    } catch (e: Exception) {
                        emit(UIState.ErrorState(e.message))
                        return@flow
                    }
                    emit(UIState.SuccessState(gitReposFromApi))
                }.collectLatest { result: UIState<GithubReposListDTO> ->
                    when (result) {
                        is UIState.ErrorState -> {
                            Log.d("in-viewModel", " update-flow-UIState-Error in ${this.javaClass}")
                            _reposApiCallUIState.update {
                                it.copy(isLoading = false)
                            }
                        }
                        is UIState.SuccessState -> {
                            Log.d("in-viewModel", " update-flow-UIState in ${this.javaClass}")
                            result.data?.let { gRepoListDTO ->
                                _reposApiCallUIState.update {
                                    it.copy(isLoading = false, githubApiCallList = gRepoListDTO)
                                }
                            }
                        }
                        is UIState.LoadingState -> {
                            Log.d("in-viewModel", " update-LiveData-UIState in ${this.javaClass}")
                            _reposApiCallUIState.update { loadingState ->
                                loadingState.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }


}