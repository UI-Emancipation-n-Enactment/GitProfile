package demo.gitprofiles.di

import android.content.Context
import demo.gitprofiles.gitreposlist.data.network.GithubreposApi
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import javax.inject.Inject

class GithubReposService @Inject constructor (
    context: Context
) {

    @Inject
    lateinit var api : GithubreposApi

    init {
        DaggerReposComponent.factory().create(context).inject(this)
    }


    suspend fun getReposList(username: String): GithubReposListDTO? {
        return api.getGithubReposList(username)
        }
}
