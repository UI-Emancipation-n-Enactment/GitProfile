package demo.gitprofiles.repos.presentation.view.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentReposUrlsBinding
import demo.gitprofiles.repos.presentation.UIState
import demo.gitprofiles.repos.presentation.view.adapter.ReposRecyclerViewAdapter
import demo.gitprofiles.repos.presentation.view.viewmodel.GithubReposListViewModel
import demo.gitprofiles.repos.presentation.view.viewmodel.GithubReposListViewModelFactory
import javax.inject.Inject


/**
 * Handles Popular Movie data (only Poster-view)
 */
class ReposUrlsFragment @Inject constructor() : Fragment(R.layout.fragment_repos_urls) {

    private lateinit var reposRecyclerViewAdapter: ReposRecyclerViewAdapter
    private var fragmentGithubReposBinding: FragmentReposUrlsBinding? = null

    private lateinit var githubReposListViewModel: GithubReposListViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val githubReposListViewModelFactory = GithubReposListViewModelFactory()
        githubReposListViewModel = ViewModelProvider(this, githubReposListViewModelFactory)[GithubReposListViewModel::class.java]
        val binding = FragmentReposUrlsBinding.bind(view)
        fragmentGithubReposBinding = binding
        setupObserver(githubReposListViewModel, binding)
    }

    private fun setupObserver (
        githubReposListViewModel: GithubReposListViewModel,
        binding: FragmentReposUrlsBinding
    ) {
        githubReposListViewModel.reposUIState.observe(viewLifecycleOwner) { uIState ->
            when (uIState) {
                is UIState.EmptyState -> {}
                is UIState.LoadingState -> {
                    uIState.isLoadingState.let { isLoadingState ->
                        if (isLoadingState) {
                            binding.apply {
                                progressBarLayout.visibility = View.VISIBLE
                                progressBarTextView.setOnClickListener {            // toggles progressBar's visibility
                                   if(cirProgressBar.isVisible){
                                       cirProgressBar.visibility = View.GONE
                                   } else {
                                       cirProgressBar.visibility = View.VISIBLE
                                   }
                                }
                            }

                        } else {
                            binding.progressBarLayout.visibility = View.GONE
                            binding.cirProgressBar.visibility = View.GONE
                        }
                    }
                }
                is UIState.SuccessState -> {
                    binding.progressBarLayout.visibility = View.GONE
                    val repos = uIState.githubReposListDTO

                    repos?.map {githubRepo->
                        binding.apply {
                            binding.imageAvatar.setOnClickListener {
                                Glide.with(root)
                                    .load(githubRepo.owner.avatarUrl)
                                    .into(imageAvatar)
                                    tvLogin.text =githubRepo.owner.login
                            }
                        }

                    }
                    repos.let {
                        it?.sortBy { repo ->
                            repo.updatedAt
                        }
                    }
                    repos?.reverse()
                    reposRecyclerViewAdapter = ReposRecyclerViewAdapter(repos)
                    binding.rViewGithubRepos.adapter = reposRecyclerViewAdapter
                    binding.rViewGithubRepos.layoutManager = GridLayoutManager(requireContext(), 1)
                    reposRecyclerViewAdapter.apply {
                    }
                }

                is UIState.ErrorState -> {
                    Toast.makeText(activity, "Error: ${uIState.error}", Toast.LENGTH_LONG).show()
                    Log.d("mLogs", "Error: ${uIState.error}")
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentGithubReposBinding = null
    }

}

