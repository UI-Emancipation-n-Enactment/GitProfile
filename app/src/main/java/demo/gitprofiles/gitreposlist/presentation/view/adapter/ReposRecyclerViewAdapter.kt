package demo.gitprofiles.gitreposlist.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.gitprofiles.databinding.ReposRowBinding
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO
import javax.inject.Inject

class ReposRecyclerViewAdapter @Inject constructor(
    private var repos: List<GithubReposDTO>,
) : RecyclerView.Adapter<ReposRecyclerViewAdapter.ReposViewHolder>() {

    private var imageClickListener: ((Any) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val repoRowPopularBinding = ReposRowBinding.inflate(layoutInflater)
        return ReposViewHolder(repoRowPopularBinding)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val repo = repos?.get(position)

        holder.bind(repo, imageClickListener)
    }

    override fun getItemCount(): Int = repos.size
    fun <T> setOnImageClickListener(listener: (T: Any) -> Unit) {
        imageClickListener = listener
    }

    class ReposViewHolder(private val binding: ReposRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (
            githubReposDTO: GithubReposDTO?,
            imageClickListener: ((GithubReposDTO) -> Unit)?
        ) {
            binding.tvRepoName.text = githubReposDTO?.name?.trim() ?: ""
            binding.navImage.setOnClickListener {
                imageClickListener?.let {
                    githubReposDTO?.let { repo -> it(repo) }
                }
            }

            binding.tvDescriptiopn.text = githubReposDTO?.description?.trim() ?: ""
        }
    }

}