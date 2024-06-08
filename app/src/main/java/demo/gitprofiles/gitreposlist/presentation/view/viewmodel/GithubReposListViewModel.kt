package demo.gitprofiles.gitreposlist.presentation.view.viewmodel

import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class GithubReposListViewModel @Inject constructor(
    private val gitProfileRepository: GitProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RepoState>(RepoState.Empty())
    val state: StateFlow<RepoState> = _state.asStateFlow()

    init {
//        getProfilesTwo()
        getProfiles()
    }

    /**
    private fun getProfilesTwo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withTimeout(2000) {
                    gitProfileRepository.getProfilesTwoImpl().collect { dataTwo: Pair<List<GithubReposDTO>, String> ->
                        when {
                            dataTwo.first.isNotEmpty() -> {
                                _state.update {
                                    RepoState.Success(dataTwo.first)
                                }
                            }
                            else -> {
                                _state.update {
                                    RepoState.Error("Error: ${dataTwo.second}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    private fun getProfiles() = viewModelScope.launch {
        try {
            val data = gitProfileRepository.getProfiles()
            when (data) {
                is RepoState.Empty -> Unit
                is RepoState.Loading -> {
                    _state.update {
                        RepoState.Loading(true)
                    }
                }
                is RepoState.Error -> {
                    _state.update {
                        RepoState.Error("Error: ${data.error}")
                    }
                }
                is RepoState.Success -> {
                    _state.update {
                        RepoState.Empty("")
                        RepoState.Success(data.data)
                    }
                }
            }
        }catch (e: Exception) {
            _state.update { failedState ->
                when(failedState) {
                    is RepoState.Empty -> {
                        RepoState.Empty("Error: ${e.cause?.message}")
                    }

                    is RepoState.Error -> RepoState.Empty("")
                    is RepoState.Loading -> RepoState.Empty("")
                    is RepoState.Success -> RepoState.Empty("")
                }
            }
            Log.e("e-exception-log", e.toString().substring(0, 29))
            Log.e("e-exception-log-cause", e.stackTrace.asList().toString())


        }
        catch (ce: CancellationException){
            Log.e("ce-exception-log", ce.printStackTrace().toString())
        }
        catch (ne: NetworkException)
        {
            Log.e("ne-exception-log", ne.printStackTrace().toString())
            RepoState.Error("Job cancellation Exception: ${ne.message as String}")
        }

        }

}