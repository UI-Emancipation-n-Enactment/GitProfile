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
        getProfiles()
    }

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
                        RepoState.Empty("${e.cause?.message?.substring(0, 26)}")
                    }
                    is RepoState.Error -> RepoState.Empty("")
                    is RepoState.Loading -> RepoState.Empty("")
                    is RepoState.Success -> RepoState.Empty("")
                }
            }
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