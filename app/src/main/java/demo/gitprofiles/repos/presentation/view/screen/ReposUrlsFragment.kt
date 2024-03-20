package demo.gitprofiles.repos.presentation.view.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun setupObserver(
        githubReposListViewModel: GithubReposListViewModel,
        binding: FragmentReposUrlsBinding
    ) {
        githubReposListViewModel.reposUIState.observe(viewLifecycleOwner) { uIState ->
            when (uIState) {
                is UIState.EmptyState -> {}
                is UIState.SuccessState -> {
                    val repos = uIState.githubReposListDTO
                    Toast.makeText(
                        activity, "Id: ${
                            repos?.map {
                                it.id
                            }
                        }", Toast.LENGTH_SHORT
                    ).show()
                    reposRecyclerViewAdapter =
                        ReposRecyclerViewAdapter(repos)                // passing data to ReposAdapter
                    binding.rViewGithubRepos.adapter = reposRecyclerViewAdapter
                    binding.rViewGithubRepos.layoutManager = LinearLayoutManager(requireContext())
                    reposRecyclerViewAdapter.apply {
                        setOnImageClickListener<String> {
//                            findNavController().navigate(
//                                ReposUrlsFragmentDirections.actionReposUrlFragmentToDetailsRepoFragment()
//                            )
                        }
                    }
                    Log.d("mLogs", "repos size: ${repos?.size}")
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