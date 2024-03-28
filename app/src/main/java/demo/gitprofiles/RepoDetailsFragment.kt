package demo.gitprofiles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import demo.gitprofiles.databinding.FragmentRepoDetailsBinding
import javax.inject.Inject

class RepoDetailsFragment @Inject constructor() : Fragment(R.layout.fragment_repo_details,) {

    private var fragmentRepoDetailsBinding : FragmentRepoDetailsBinding? = null

    private var repoNameRcvd : String? = ""
    private var languageRcvd : String? = ""
    private var htmlUrlRcvd : String? = ""
    private var createdAtRcvd : String? = ""
    private var updatedAtRcvd : String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepoDetailsBinding.bind(view)
        fragmentRepoDetailsBinding = binding

        binding.customBarDetails.setTextVisible(true)

        val received: Bundle? = arguments
        received?.apply {
            repoNameRcvd = getString("repoName")
            languageRcvd = getString("language")
            htmlUrlRcvd = getString("repoUrl")
            createdAtRcvd = getString("createdAt")
            updatedAtRcvd = getString("updatedAt")
        }

        binding.apply {
            customBarDetails.setTextVisible(true)
            if(received?.isEmpty == false) {
                binding.repoName.text = repoNameRcvd
                binding.repoLanguage.text = languageRcvd
                binding.htmlUrl.text = htmlUrlRcvd
                binding.created.text = createdAtRcvd?.substringBefore("T")
                binding.updated.text = updatedAtRcvd?.substringBefore("T")
                customBarDetails.visibility = View.GONE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoDetailsBinding = null
    }
}