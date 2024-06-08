package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
 ) : GitProfileRepository {
//     override suspend fun getProfilesTwoImpl(): Flow<Pair<List<GithubReposDTO>, String>> {
//
//         var remoteResult: Pair<List<GithubReposDTO>, String>
//         try {
//             val remoteOne = githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>
//             remoteResult = Pair(remoteOne, "")
//         } catch (e: retrofit2.HttpException) {
//             remoteResult = Pair(emptyList(), e.response()?.code().toString())
//         }
//         return flowOf(remoteResult)
//
////        return flow<List<GithubReposDTO>> {
////            val remoteData: List<GithubReposDTO> = try {
////                githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>       // incorrect URL, expected error-code: 404
////            } catch (e: retrofit2.HttpException){
////                Log.e("e_wrong_repos", e.response().toString())
////                emit(emptyList())       // goes into the 'else' block in ViewModel
////                return@flow
////            }
////            emit(remoteData)
////        }
//
//        /** ----------------------------------------------------------------------------------------------------------------- **/
//
////        var remoteDataFlow: Flow<List<GithubReposDTO>>
////        try {
////            remoteDataFlow =  flowOf(githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>)     // incorrect URL, expected error-code: 404
////        } catch (e: retrofit2.HttpException) {
////            Log.e("e_wrong_repos", e.response().toString())
////            remoteDataFlow = flowOf(emptyList())
////        }
////        return remoteDataFlow
//        /** ----------------------------------------------------------------------------------------------------------------- **/
//
//    }

    /** ----------------------------------------------------------------------------------------------------------------- *
     * The below approach is good when we do expect the date (not as Flow) to be latest (not needed to be auto-updated)
     * Example: a remote-Api call
     *
     * */
    override suspend fun getProfiles(): RepoState {
        val remoteResultRepoState: RepoState = try {
            RepoState.Success( githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>)
        }catch (e: retrofit2.HttpException){
            RepoState.Error(e.response()?.code().toString())
        }
        return remoteResultRepoState
    }

    /** ----------------------------------------------------------------------------------------------------------------- *
     * The below approach is good when we do expect the date (nas Flow) to be needed as auto-update
     * Example: fetching local DB-data, thant will not be correct if not being 'Observable'
     *

    override suspend fun getProfiles(): Flow<RepoState> {
        var remoteResultRepoState: RepoState
        try {
           remoteResultRepoState = RepoState.Success( githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>)
        }catch (e: retrofit2.HttpException){
            remoteResultRepoState = RepoState.Error(e.response()?.code().toString())
        }
        return flowOf(remoteResultRepoState)
    }
    */


}