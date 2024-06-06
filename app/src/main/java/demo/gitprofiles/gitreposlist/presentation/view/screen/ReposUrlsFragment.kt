package demo.gitprofiles.gitreposlist.presentation.view.screen

import android.graphics.Color
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.Icon
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentReposUrlsBinding
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
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
                        is RepoState.Empty -> RepoState.Empty
                        is RepoState.Error -> {

                            binding.customProgressMain.visibility = View.GONE
                            val error = state.error
                            val errorString = "Error $error"
                            val categoryStart = errorString.indexOf(error)
                            val categoryEnd = error.length
                            binding.textView2.apply {
                                visibility = View.VISIBLE
                                textSize = 32f
                                NORMAL
                                text = SpannableString(state.error). apply {
                                setSpan(
                                    ForegroundColorSpan(Color.RED),
                                    categoryStart,
                                    categoryEnd,
                                    0
                                )
                            }
                            }
                            binding.imageAvatar.setImageIcon(
                                Icon.createWithResource(
                                    requireContext(),
                                    R.drawable.ic_launcher_foreground
                                )
                            )
                        }
                        is RepoState.LoadingState -> {
                            binding.customProgressMain.visibility = View.VISIBLE
                        }
                        is RepoState.Success -> {
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





