package demo.gitprofiles.repos.presentation.view.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import demo.githubrepos.di.DaggerReposComponent
import demo.gitprofiles.di.GithubReposService
import javax.inject.Inject

class GithubReposListViewModelFactory(
) : ViewModelProvider.Factory {

     @Inject
     lateinit var githubReposService: GithubReposService

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        DaggerReposComponent.create().inject(this)
        return GithubReposListViewModel(githubReposService = githubReposService) as T
    }
}


