package demo.gitprofiles.di

import demo.githubrepos.di.DaggerReposComponent
import demo.gitprofiles.repos.data.remote.GithubreposApi
import demo.gitprofiles.repos.data.remote.response.GithubReposListDTO
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
