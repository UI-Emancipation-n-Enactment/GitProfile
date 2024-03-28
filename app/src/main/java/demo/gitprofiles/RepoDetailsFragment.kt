package demo.gitprofiles

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import demo.gitprofiles.databinding.FragmentRepoDetailsBinding
import javax.inject.Inject

class RepoDetailsFragment @Inject constructor() : Fragment(R.layout.fragment_repo_details) {

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
        binding.customBarDetails.visibility = View.VISIBLE

        val received: Bundle? = arguments
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
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentRepoDetailsBinding = null
    }
}