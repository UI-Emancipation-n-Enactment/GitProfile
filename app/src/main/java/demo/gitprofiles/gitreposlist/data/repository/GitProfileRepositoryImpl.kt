package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.domain.repository.GitProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GitProfileRepositoryImpl @Inject constructor(
    private val githubReposService: GithubReposService,
 ) : GitProfileRepository {
     override suspend fun getProfilesTwoImpl(): Flow<Pair<List<GithubReposDTO>, String>> {

         var remoteResult: Pair<List<GithubReposDTO>, String> = Pair(emptyList(), "")
         try {
             val remoteOne = githubReposService.getReposList("snaqviApps") as List<GithubReposDTO>
             remoteResult = Pair(remoteOne, "")

         } catch (e: retrofit2.HttpException) {
             remoteResult = Pair(emptyList(), e.response()?.code().toString())
         }
         return flowOf(remoteResult)

//        return flow<List<GithubReposDTO>> {
//            val remoteData: List<GithubReposDTO> = try {
//                githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>       // incorrect URL, expected error-code: 404
//            } catch (e: retrofit2.HttpException){
//                Log.e("e_wrong_repos", e.response().toString())
//                emit(emptyList())       // goes into the 'else' block in ViewModel
//                return@flow
//            }
//            emit(remoteData)
//        }

        /** ----------------------------------------------------------------------------------------------------------------- **/

//        var remoteDataFlow: Flow<List<GithubReposDTO>>
//        try {
//            remoteDataFlow =  flowOf(githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>)     // incorrect URL, expected error-code: 404
//        } catch (e: retrofit2.HttpException) {
//            Log.e("e_wrong_repos", e.response().toString())
//            remoteDataFlow = flowOf(emptyList())
//        }
//        return remoteDataFlow
        /** ----------------------------------------------------------------------------------------------------------------- **/
//        var remoteResult : List<GithubReposDTO> = mutableListOf<GithubReposDTO>()
//        remoteResult = try {
//            githubReposService.getReposList("snaqviApps_") as List<GithubReposDTO>
//        }catch (e: retrofit2.HttpException){
//    //            val error = e.message()
//    //            replace with error message body
//            emptyList()
//        }
//        return flowOf(remoteResult)
        /** ----------------------------------------------------------------------------------------------------------------- **/

    }


}