package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
 ) : GitProfileRepository {

    override suspend fun getProfiles(): Flow<List<GithubReposDTO>> =
        runCatching {
            githubReposService.getReposList("snaqviApps")
        }.getOrNull()?.let {
            return flowOf(it)
        } ?: run {
            return flowOf(emptyList())
        }

    override suspend fun getProfilesTwo(): Flow<List<GithubReposDTO>> {

        //1 . get the list of repos from the service, wrap it in a flow
        return flow<List<GithubReposDTO>>  {
            val remoteData : List<GithubReposDTO> = try {
                githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>
            }catch (e:Exception){
                e.printStackTrace()
                return@flow
            }
            emit(remoteData)

        }

        // 3. handle error below
//        githubReposService.getReposList("snaqviApps_two")


    }
}