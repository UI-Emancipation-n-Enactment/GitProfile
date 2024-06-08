package demo.gitprofiles.gitreposlist.presentation.view.screen

import android.graphics.Color
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.util.wrapMappedColumns
import com.bumptech.glide.Glide
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentReposUrlsBinding
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import demo.gitprofiles.gitreposlist.presentation.view.adapter.ReposRecyclerViewAdapter
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.System.exit
import javax.inject.Inject
import kotlin.system.exitProcess


/**
 * Handles Popular Movie data (only Poster-view)
 */
class ReposUrlsFragment @Inject constructor() : Fragment(R.layout.fragment_repos_urls) {
    private var fragmentGithubReposBinding: FragmentReposUrlsBinding? = null
    private lateinit var githubReposListViewModel: GithubReposListViewModel

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val githubReposListViewModelFactory = GithubReposListViewModelFactory()
        githubReposListViewModel = ViewModelProvider(
            requireActivity(),
            githubReposListViewModelFactory
        )[GithubReposListViewModel::class.java]

        val binding = FragmentReposUrlsBinding.bind(view)
        fragmentGithubReposBinding = binding
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                githubReposListViewModel.state.collectLatest { state ->
                    when (state) {
                        is RepoState.Empty -> {
                            if(state.nwException != "") {
                                binding.apply {
                                    rViewGithubRepos.visibility = View.GONE
                                    customProgressMain.visibility = View.GONE
                                    textView.visibility = View.GONE
                                    textView2.visibility = View.GONE
                                    connectionErrorView.apply {
                                        visibility = View.VISIBLE
                                        textSize = 24f
                                        rotation = -12f
                                        gravity = Gravity.CENTER
                                        setTextColor(Color.RED)
                                        setBackgroundColor(Color.LTGRAY)
                                        text = state.nwException.plus(
                                            "\n\n" + "Please check your connectivity!")
                                    }
                                }
                            }
                        }
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
                        is RepoState.Loading -> {
                            binding.customProgressMain.visibility = View.VISIBLE

                        }
                        is RepoState.Success -> {
                            binding.textView.visibility = View.VISIBLE
                            binding.connectionErrorView.visibility = View.GONE
                            binding.tvLogin.visibility = View.VISIBLE
                            binding.imageAvatar.visibility = View.VISIBLE
                            binding.customProgressMain.visibility = View.GONE
                            binding.rViewGithubRepos.visibility = View.VISIBLE
                            binding.imageAvatar.apply {
                                setOnClickListener {
                                    state.data.map {
                                        binding.tvLogin.text = it.owner.login
                                        Glide.with(binding.root)
                                            .load(it.owner.avatarUrl)
                                            .into(binding.imageAvatar)
                                    }
                                }
                            }
                            ReposRecyclerViewAdapter(state.data).apply {
                                binding.rViewGithubRepos.adapter = this
                                binding.rViewGithubRepos.layoutManager = GridLayoutManager(requireContext(), 1)
                                setOnImageClickListener<GithubReposDTO?> { repo: Any ->
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





