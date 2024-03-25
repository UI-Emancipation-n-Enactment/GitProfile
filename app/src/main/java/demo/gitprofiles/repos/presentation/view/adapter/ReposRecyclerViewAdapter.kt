package demo.gitprofiles.repos.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.gitprofiles.databinding.ReposRowBinding
import demo.gitprofiles.repos.data.network.response.GithubReposDTO
import demo.gitprofiles.repos.data.network.response.GithubReposListDTO
import javax.inject.Inject

class ReposRecyclerViewAdapter @Inject constructor(
    private var repos: GithubReposListDTO?,
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

    override fun getItemCount(): Int = repos?.size ?: 0
    fun <T> setOnImageClickListener(listener: (T: Any) -> Unit) {
        imageClickListener = listener
    }

    class ReposViewHolder(private val binding: ReposRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            githubReposDTO: GithubReposDTO?,
            imageClickListener: ((String) -> Unit)?
        ) {
            binding.apply {
                tvRepoName.apply {
                    text = githubReposDTO?.name?.trim() ?: ""
                    setOnClickListener {
                        imageClickListener?.let {
                            it(githubReposDTO?.id.toString())
                        }
                    }
                }
                tvDescriptiopn.text = githubReposDTO?.description?.trim() ?: ""
            }
        }
    }


}