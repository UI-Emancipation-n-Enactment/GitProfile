package demo.gitprofiles.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import demo.gitprofiles.di.module.DatabaseModule
import demo.gitprofiles.di.module.GitRepositoryModule
import demo.gitprofiles.di.module.RepositoryModule
import demo.gitprofiles.gitreposlist.data.repository.GitProfileRepositoryImpl
import demo.gitprofiles.gitreposlist.presentation.view.screen.ReposUrlsFragment
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModelFactory
import javax.inject.Singleton

/**
 * This class contains the 'injection' mechanism for the destination below, as:
 *  There are Three possible-destinations, that could receive the dependencies available in
 *  GitRepositoryModule-class using the method-call ReposComponent.factory().create(context).inject()
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
        RepositoryModule::class,
        DatabaseModule::class
    ]
)
interface ReposComponent {
    /** provided Application Context, required at run-time, here,
     * that is needed to create (this) RepoComponent, as it would otherwise cause the
     * 'not initialized context' Error
     * */
    @Component.Factory
    interface
    ContextFactory {
        fun create(@BindsInstance context: Context): ReposComponent         // binds application-Context to this Component, Soon application runs
    }

    fun inject(reposUrlsFragment: ReposUrlsFragment)
    fun inject(githubReposService: GithubReposService)
    fun inject(githubProfileRepositoryImpl: GitProfileRepositoryImpl)
    fun inject(moviesListViewModel: GithubReposListViewModel)
    fun inject(viewModelFactory: GithubReposListViewModelFactory)
}