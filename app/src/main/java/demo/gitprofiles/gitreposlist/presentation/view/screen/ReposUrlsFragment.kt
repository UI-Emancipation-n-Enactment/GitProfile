package demo.gitprofiles.gitreposlist.presentation.view.screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentReposUrlsBinding
import demo.gitprofiles.gitreposlist.presentation.view.UiState
import demo.gitprofiles.gitreposlist.presentation.view.adapter.RVAdapter
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Handles Popular Movie data (only Poster-view)
 */
class ReposUrlsFragment @Inject constructor() : Fragment(R.layout.fragment_repos_urls) {
    private var fragmentGithubReposBinding: FragmentReposUrlsBinding? = null
    private lateinit var githubReposListViewModel: GithubReposListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val githubReposListViewModelFactory = GithubReposListViewModelFactory()
        githubReposListViewModel = ViewModelProvider(
            this,
            githubReposListViewModelFactory
        )[GithubReposListViewModel::class.java]
        val binding = FragmentReposUrlsBinding.bind(view)
        fragmentGithubReposBinding = binding

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                githubReposListViewModel.state.collectLatest { state ->
                    when (state) {
                        is UiState.Empty -> UiState.Empty
                        is UiState.Error -> {
                            binding.customProgressMain.visibility = View.GONE
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                        }
                        is UiState.LoadingState -> {
                            binding.customProgressMain.visibility = View.VISIBLE
                        }

                        is UiState.Success -> {
                            binding.customProgressMain.visibility = View.GONE
                            val adapter = RVAdapter(state.data)
                            binding.rViewGithubRepos.adapter = adapter
                            binding.rViewGithubRepos.layoutManager =
                                GridLayoutManager(requireContext(), 1)
                        }


                    }
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentGithubReposBinding = null
    }

}

fun <T> Fragment.collectLatestLifecycleFlowChanges(
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





