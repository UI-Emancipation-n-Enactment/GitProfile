package demo.gitprofiles.gitreposlist.data.repository

import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking

import org.junit.Test

class GitProfileRepositoryImplTest {

    @Test
    fun `get profiles from github with mock data`() {

        val mockGithubApi = mockk<GithubReposService>()

        /**
         * Starts a block of stubbing for coroutines. Part of DSL. Similar to every, but works with suspend functions.
         * Used to define what behaviour is going to be mocked.
         */
        coEvery { mockGithubApi.getReposList("mockK-userName") } returns GithubReposListDTO()

        runBlocking {
          val mockKGithubReposListDTOResponse = mockGithubApi.getReposList("mockK-userName")
            assertNotNull(mockKGithubReposListDTOResponse)
        }

    }

}