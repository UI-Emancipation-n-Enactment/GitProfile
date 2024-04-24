package demo.gitprofiles.di

import demo.gitprofiles.gitreposlist.data.network.GithubreposApi
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import javax.inject.Inject

class GithubReposService @Inject constructor (
    private val api:GithubreposApi
) {
    suspend fun getReposList(username: String): GithubReposListDTO? {
        return api.getGithubReposList(username)
        }
}
