package demo.gitprofiles.repos.data.network

import demo.gitprofiles.repos.data.network.response.GithubReposListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubreposApi {
    @GET("{username}/repos")
    suspend fun getGithubReposList (
        @Path("username") username: String = "",      // default
    ) : GithubReposListDTO?
}