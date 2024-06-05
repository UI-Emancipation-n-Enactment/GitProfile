package demo.gitprofiles.gitreposlist.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.view.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class GithubReposListViewModel @Inject constructor(
    private val gitProfileRepository: GitProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Empty)
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        getProfilesTwo()
    }
    private fun getProfilesTwo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withTimeout(2000) {
                    gitProfileRepository.getProfilesTwoImpl().collect { dataTwo: Pair<List<GithubReposDTO>, String> ->
                        when {
                            dataTwo.first.isNotEmpty() -> {
                                _state.update {
                                    UiState.Success(dataTwo.first)
                                }
                            }
                            else -> {
                                _state.update {
                                    UiState.Error("Error: ${dataTwo.second}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}