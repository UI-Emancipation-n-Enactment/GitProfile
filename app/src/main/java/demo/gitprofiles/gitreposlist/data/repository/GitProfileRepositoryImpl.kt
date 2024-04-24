package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.local.profileDB.GitProfileDetailsDatabase
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import demo.gitprofiles.gitreposlist.data.network.response.toGitProfileEntity
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
    private val gitProfileDetailsDatabase: GitProfileDetailsDatabase
) : GitProfileRepository {
    override suspend fun getGitProfiles()
    : Flow<UIState<GithubReposListDTO>> {
        return flow<UIState<GithubReposListDTO>> {
             val gitReposFromApi: GithubReposListDTO? = try {
                githubReposService.getReposList("snaqviApps")
            } catch (e: Exception) {
                emit(UIState.ErrorState(e.message))
                return@flow
             }
            // insert the repository-info to profileDB
            gitReposFromApi?.toGitProfileEntity()?.let {
                gitProfileDetailsDatabase.gitProfileDAO().upsertGitProfile(it)
            }
            emit(UIState.SuccessState(gitReposFromApi))

        }
    }
}
