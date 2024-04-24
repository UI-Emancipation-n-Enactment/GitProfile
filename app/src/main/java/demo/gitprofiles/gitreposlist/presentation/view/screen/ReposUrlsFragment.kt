package demo.gitprofiles.gitreposlist.presentation.view.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import demo.gitprofiles.GitProfileApp
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentReposUrlsBinding
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposListDTO
import demo.gitprofiles.gitreposlist.presentation.view.GitHubReposListUIState
import demo.gitprofiles.gitreposlist.presentation.view.adapter.ReposRecyclerViewAdapter
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/** Landing screen
 * @author
 * <a href="mailto: naqvie@gmail.com">
 * */
class ReposUrlsFragment @Inject constructor() : Fragment(R.layout.fragment_repos_urls) {
    private var fragmentGithubReposBinding: FragmentReposUrlsBinding? = null
    private lateinit var githubReposListViewModel: GithubReposListViewModel

    @Inject
    lateinit var viewModelFactory: GithubReposListViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as GitProfileApp).reposComponent.inject(this)
        githubReposListViewModel = ViewModelProvider(this, viewModelFactory)[GithubReposListViewModel::class.java]

        fragmentGithubReposBinding = FragmentReposUrlsBinding.bind(view)
        val binding = FragmentReposUrlsBinding.bind(view)
        fragmentGithubReposBinding = binding

        /** collect data-as-state from viewModel */
        val gData: StateFlow<GitHubReposListUIState> = githubReposListViewModel.reposApiCallUIState
        collectLatestLifecycleFlowChanges(requireActivity(), gData) { gState ->
            if(!gState.isLoading) {
                binding.customProgressMain.visibility = View.GONE
            } else {
                binding.customProgressMain.visibility = View.VISIBLE
            }

            val repos = gState.githubApiCallList
            when (repos.isNotEmpty()) {
                true -> {
                    repos.sortedByDescending { it.updatedAt }
                    ReposRecyclerViewAdapter(repos as GithubReposListDTO).apply {
                        binding.rViewGithubRepos.adapter = this
                        binding.rViewGithubRepos.layoutManager =
                            GridLayoutManager(requireContext(), 1)
                        setOnImageClickListener<GithubReposDTO?> { repo ->
                            findNavController().navigate(
                                ReposUrlsFragmentDirections.actionReposUrlsFragmentToRepoDetailsFragment(
                                    (repo as GithubReposDTO).name,
                                    repo.language,
                                    repo.htmlUrl,
                                    repo.createdAt,
                                    repo.pushedAt
                                )
                            )
                        }
                    }
                    repos.map { githubRepo ->
                        binding.apply {
                            tvLogin.text = githubRepo.owner.login
                            binding.imageAvatar.setOnClickListener {
                                Glide.with(root)
                                    .load(githubRepo.owner.avatarUrl)
                                    .into(imageAvatar)
                            }
                        }
                    }
                }
                false -> {
                    binding.customProgressMain.visibility = View.VISIBLE
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        fragmentGithubReposBinding = null
    }

}

fun <T> ReposUrlsFragment.collectLatestLifecycleFlowChanges(
    activity: FragmentActivity,
    flow: Flow<T>,
    collectInfo: suspend (T) -> Unit
) {
    activity.lifecycleScope.launch {
        activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collectInfo)
        }
    }
}





