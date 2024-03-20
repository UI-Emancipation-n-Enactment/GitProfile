package demo.gitprofiles.repos.presentation.view.screen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
                is UIState.SuccessState -> {
                    val repos = uIState.githubReposListDTO
                    repos?.map {
                        Glide.with(binding.root)
                            .load(it.owner.avatarUrl)
                            .into(binding.imageAvatar)

                        binding.apply {
                            tvLogin.text = it.owner.login

                        }

                    }
                    repos?.reverse()
                    reposRecyclerViewAdapter = ReposRecyclerViewAdapter(repos)                // passing data to ReposAdapter
                    binding.rViewGithubRepos.adapter = reposRecyclerViewAdapter
                    binding.rViewGithubRepos.layoutManager = LinearLayoutManager(requireContext())
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

