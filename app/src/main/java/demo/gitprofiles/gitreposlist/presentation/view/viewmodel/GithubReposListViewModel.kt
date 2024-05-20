package demo.gitprofiles.gitreposlist.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.view.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        getProfiles()
        getProfilesTwo()
    }

    private fun getProfilesTwo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withTimeout(2000) {
                    gitProfileRepository.getProfilesTwo().collect { dataTwo ->
                        when {
                            dataTwo.isNotEmpty() -> {
                                _state.update {
                                    UiState.Success(dataTwo)
                                }
                            }
                            else -> _state.value = UiState.Error("No data found")       // creating new instance
                        }
                    }
                }
            }
        }
    }

    private fun getProfiles() = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            UiState.LoadingState
        }
        delay(2000)
        gitProfileRepository.getProfiles().collect { data ->
            when {
//                data.isNotEmpty() -> _state.value = UiState.Success(data)
                data.isNotEmpty() -> {
                    _state.update {
                        UiState.Success(data)
                    }
                }

                else -> _state.value = UiState.Error("No data found")
            }
        }

    }
}