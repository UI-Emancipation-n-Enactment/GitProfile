package demo.gitprofiles.gitreposlist.presentation.view.screen

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import demo.gitprofiles.R
import demo.gitprofiles.databinding.FragmentRepoDetailsBinding
import demo.gitprofiles.gitreposlist.presentation.view.RepoState
import demo.gitprofiles.gitreposlist.presentation.view.viewmodel.GithubReposListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoDetailsFragment @Inject constructor() : Fragment(R.layout.fragment_repo_details) {

    private var fragmentRepoDetailsBinding : FragmentRepoDetailsBinding? = null

    private var repoNameRcvd : String? = ""
    private var languageRcvd : String? = ""
    private var htmlUrlRcvd : String? = ""
    private var createdAtRcvd : String? = ""
    private var updatedAtRcvd : String? = ""

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val received: Bundle? = arguments
        val binding = FragmentRepoDetailsBinding.bind(view)
        fragmentRepoDetailsBinding = binding
        val detailsViewModel: GithubReposListViewModel by lazy {
            ViewModelProvider(
                requireActivity())[GithubReposListViewModel::class.java]
        }

        binding.customBarDetails.setTextVisible(true)
        binding.customBarDetails.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            detailsViewModel.state.collectLatest { detailsState ->
                when (detailsState) {
                    is RepoState.Empty -> {
                        Toast.makeText(requireContext(), detailsState.nwException, Toast.LENGTH_SHORT).show()
                    }

                    is RepoState.Error -> {
                        Toast.makeText(requireContext(), detailsState.error, Toast.LENGTH_SHORT).show()
                    }
                    is RepoState.Loading -> {
//                        binding.customBarDetails.setTextVisible()
                        binding.customBarDetails.visibility = View.VISIBLE
                    }
                    is RepoState.Success -> {
                        val nameReceived = received?.getString("repoName")
                        binding.apply {
                            customBarDetails.visibility = View.GONE
                            detailsState.data.listIterator().forEach {
                                if (it.name == nameReceived) {
                                    repoName.text = it.name
                                    repoLanguage.text = it.language
                                    created.text = it.createdAt.substringBefore("T")
                                    updated.text = it.updatedAt.substringBefore("T")

                                    val urlR = it.htmlUrl
                                    val linkedText = String.format("<a href=\"%s\">$urlR</a> ", urlR)
                                    htmlUrl.apply {
                                        text = Html.fromHtml(linkedText, 0)
                                        setOnClickListener {
                                            repoWView.visibility = View.VISIBLE
                                            repoWView.loadUrl(urlR)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
        /***
        received?.apply {
            repoNameRcvd = getString("repoName")
            languageRcvd = getString("language")
            htmlUrlRcvd = getString("repoUrl")
            createdAtRcvd = getString("createdAt")
            updatedAtRcvd = getString("updatedAt")
        }

        binding.apply {
            if(received?.isEmpty == false) {
                repoName.text = repoNameRcvd
                repoLanguage.text = languageRcvd
                htmlUrl.text = htmlUrlRcvd
                created.text = createdAtRcvd?.substringBefore("T")
                updated.text = updatedAtRcvd?.substringBefore("T")
                binding.customBarDetails.setTextVisible()

                customBarDetails.visibility = View.GONE
            }

            val urlR = htmlUrl.text.toString()
            val linkedText = java.lang.String.format("<a href=\"%s\">$urlR</a> ", urlR)
            htmlUrl.text = Html.fromHtml(linkedText, 0)
            htmlUrl.setOnClickListener {
                repoWView.visibility = View.VISIBLE
                repoWView.loadUrl(urlR)
            }
        }
        ***/

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoDetailsBinding = null
    }
}