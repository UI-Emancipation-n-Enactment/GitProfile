package demo.gitprofiles.di

import dagger.Component
import demo.gitprofiles.di.module.GitRepositoryModule
import demo.gitprofiles.di.module.RepositoryModule
import demo.gitprofiles.gitreposlist.data.repository.GitProfileRepositoryImpl
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModelFactory
import javax.inject.Singleton

/**
 * This class contains the 'injection' mechanism for the destination below, as:
 *  There are Three possible-destinations, that could receive the dependencies available in
 *  MovieModule-class using the method-call MoviesComponent.create().inject()
 *
 *  1. MoviesService
 *  2. MoviesListRepository
 *  3. MoviesListViewModel
 *
 */

@Singleton
@Component(
    modules = [
        GitRepositoryModule::class,
        RepositoryModule::class]
)
interface ReposComponent {

    fun inject(moviesService: GithubReposService)

    fun inject(githubProfileRepositoryImpl: GitProfileRepositoryImpl)

    fun inject(moviesListViewModel: GithubReposListViewModel)
    fun inject(viewModelFactory: GithubReposListViewModelFactory)

}