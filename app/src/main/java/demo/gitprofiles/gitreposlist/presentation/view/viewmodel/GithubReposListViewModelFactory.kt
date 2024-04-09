package demo.gitprofiles.gitreposlist.presentation.view.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demo.gitprofiles.di.DaggerReposComponent
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import javax.inject.Inject

class GithubReposListViewModelFactory (
) : ViewModelProvider.Factory {

     @Inject
     lateinit var gitProfileRepository: GitProfileRepository

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        DaggerReposComponent.create().inject(this)
        return GithubReposListViewModel(
            gitProfileRepository = gitProfileRepository,
        ) as T
    }
}


