package demo.gitprofiles.di

import demo.gitprofiles.repos.data.network.GithubreposApi
import demo.gitprofiles.repos.data.network.response.GithubReposListDTO
import javax.inject.Inject

class GithubReposService {

    @Inject
    lateinit var api : GithubreposApi

    init {
      DaggerReposComponent.create().inject(this)
    }

    suspend fun getRepos(username: String): GithubReposListDTO? {
        return api.getGithubReposList(username)
        }

}
