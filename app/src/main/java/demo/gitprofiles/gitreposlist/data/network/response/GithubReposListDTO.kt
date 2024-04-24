package demo.gitprofiles.gitreposlist.data.network.response

import demo.gitprofiles.gitreposlist.data.local.profileDB.GitProfileEntity


class GithubReposListDTO : ArrayList<GithubReposDTO>()

fun GithubReposListDTO.toGitProfileEntity(): ArrayList<GitProfileEntity> {
    val gitProfileEntity = ArrayList<GitProfileEntity>()
    this.forEach {
        gitProfileEntity.add(
            GitProfileEntity(
                id = it.id,
                Name = it.name,
                Language = it.language,
                Url = it.htmlUrl,
                Created = it.createdAt,
                Updated = it.pushedAt
            )
        )
    }
    return gitProfileEntity

}

