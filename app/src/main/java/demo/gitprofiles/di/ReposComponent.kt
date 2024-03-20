package demo.githubrepos.di

import dagger.Component
import demo.githubrepos.di.module.RepoModule
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.repos.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.repos.presentation.view.viewmodel.GithubReposListViewModelFactory
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
@Component(modules = [RepoModule::class]
)
interface ReposComponent {

    fun inject(moviesService: GithubReposService)

    fun inject(moviesListViewModel: GithubReposListViewModel)
    fun inject(viewModelFactory: GithubReposListViewModelFactory)

}