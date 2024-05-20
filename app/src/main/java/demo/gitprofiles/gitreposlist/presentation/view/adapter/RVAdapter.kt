package demo.gitprofiles.gitreposlist.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.gitprofiles.databinding.ReposRowBinding
import demo.gitprofiles.gitreposlist.data.network.response.GithubReposDTO

class RVAdapter(private val data: List<GithubReposDTO>) :
    RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ReposRowBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    inner class ViewHolder(private val binding: ReposRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: GithubReposDTO) {
            binding.tvRepoName.text = data.name.trim()
            binding.tvDescriptiopn.text = data.description?.trim() ?: ""
        }

    }

}