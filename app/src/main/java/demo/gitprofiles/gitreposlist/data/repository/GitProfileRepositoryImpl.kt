package demo.gitprofiles.gitreposlist.data.repository

import android.util.Log
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
 ) : GitProfileRepository {

    override suspend fun getProfilesTwoImpl(): Flow<List<GithubReposDTO>> {
        return flow {
            val remoteData: List<GithubReposDTO> = try {
                githubReposService.getReposList("snaqviApps_Two") as List<GithubReposDTO>       // incorrect URL, expected error-code: 404
//                githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>
            } catch (e: retrofit2.HttpException){
                Log.e("e_wrong_repos", e.response().toString())
                emit(emptyList())       // goes into the 'else' block in ViewModel
                return@flow
            }
            emit(remoteData)
        }

    }


}