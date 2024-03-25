package demo.gitprofiles.repos.presentation.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.repos.presentation.UIState
import demo.gitprofiles.repos.util.CoreConstants.Companion.MAX_TIME_OUT
import javax.inject.Inject

class GithubReposListViewModel @Inject constructor (
    private val githubReposService: GithubReposService
) : ViewModel() {

    private val _reposUIState = MutableLiveData<UIState>()
    val reposUIState: LiveData<UIState> = _reposUIState

    init {
        fetchGithubRepos()
    }

    private fun fetchGithubRepos() {
        Log.d("in-viewModel", "calling prepareGithubRepos")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withTimeout(MAX_TIME_OUT) {
                    Log.d("in-viewModel", " update-LiveData-UIState in ${this.javaClass}")
                    try {
                        _reposUIState.postValue(UIState.LoadingState(true))
                        val reposInfo = githubReposService.getRepos("snaqviApps")
                        if (reposInfo?.isNotEmpty() == true) {
                            Log.d("in-repo: ", "${reposInfo.size}")
                            _reposUIState.postValue(UIState.SuccessState(githubReposListDTO = reposInfo))
                        } else {
                            _reposUIState.postValue(UIState.ErrorState("data retrieval error"))
                        }
                    } catch (exception: Exception) {
                        exception.message.let {
                            _reposUIState.postValue(it?.let { exceptionMessage ->
                                UIState.ErrorState(exceptionMessage)
                            })
                        }
                    }
                }
            }
        }
    }

}