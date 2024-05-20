package demo.gitprofiles.gitreposlist.domain.repository

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import kotlinx.coroutines.flow.Flow

interface GitProfileRepository {
    suspend fun getProfiles(): Flow<List<GithubReposDTO>>
    suspend fun getProfilesTwo(): Flow<List<GithubReposDTO>>

}