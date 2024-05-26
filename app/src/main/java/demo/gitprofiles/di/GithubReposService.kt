package demo.gitprofiles.di

import demo.gitprofiles.gitreposlist.data.network.GithubreposApi
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import javax.inject.Inject

class GithubReposService {

    @Inject
    lateinit var api: GithubreposApi

    init {
        DaggerReposComponent.create().inject(this)
    }

//    suspend fun getReposList(username: String): GithubReposListDTO? = api.getGithubReposList(username)
    suspend fun getReposList(username: String) = api.getGithubReposList(username)

}
