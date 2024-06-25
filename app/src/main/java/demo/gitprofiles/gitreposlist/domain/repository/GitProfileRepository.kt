package demo.gitprofiles.gitreposlist.domain.repository

import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import kotlinx.coroutines.flow.Flow

interface GitProfileRepository {
    suspend fun getProfiles(): RepoState
}