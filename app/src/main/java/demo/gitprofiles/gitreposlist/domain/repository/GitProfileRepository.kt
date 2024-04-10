package demo.gitprofiles.gitreposlist.domain.repository

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import demo.gitprofiles.gitreposlist.presentation.UIState
import kotlinx.coroutines.flow.Flow

interface GitProfileRepository {
    suspend fun getGitProfiles(): Flow<UIState<GithubReposListDTO>>
}