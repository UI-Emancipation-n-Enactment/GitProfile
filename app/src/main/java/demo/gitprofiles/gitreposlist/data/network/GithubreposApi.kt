package demo.gitprofiles.gitreposlist.data.network

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubreposApi {
    @GET("{username}/repos")
    suspend fun getGithubReposList (
        @Path("username") username: String = "",      // default
    ) : GithubReposListDTO?
}