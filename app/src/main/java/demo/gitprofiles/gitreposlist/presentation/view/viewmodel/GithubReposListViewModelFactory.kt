package demo.gitprofiles.gitreposlist.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import javax.inject.Inject

class GithubReposListViewModelFactory @Inject constructor (
    private val gitProfileRepository: GitProfileRepository
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return GithubReposListViewModel(
            gitProfileRepository = gitProfileRepository
        ) as T
    }
}


