package demo.gitprofiles.di.module

import dagger.Binds
import dagger.Module
import demo.gitprofiles.gitreposlist.data.repository.GitProfileRepositoryImpl
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesGitProfileRepository(
        gitRepositoryImpl: GitProfileRepositoryImpl
    ): GitProfileRepository

}