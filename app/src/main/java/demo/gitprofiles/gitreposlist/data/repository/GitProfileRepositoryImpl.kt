package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import javax.inject.Inject

/**
 * This class implement repository interface,
 * more it comes with some 'Notes' on different approaches of 'Flow'
 */
class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
 ) : GitProfileRepository {

    override suspend fun getProfiles(): RepoState {
        val remoteResultRepoState: RepoState = try {
            RepoState.Success( githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>)
        }catch (e: retrofit2.HttpException){
            RepoState.Error((e.message as String).plus(" page not found "))
        }
        return remoteResultRepoState
    }

}