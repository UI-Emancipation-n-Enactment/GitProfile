package demo.gitprofiles.gitreposlist.data.network.response

import demo.gitprofiles.gitreposlist.data.local.profileDB.GitProfileEntity


class GithubReposListDTO : ArrayList<GithubReposDTO>()

fun GithubReposListDTO.toGitProfileEntity(): ArrayList<GitProfileEntity> {
    val gitProfileEntity = ArrayList<GitProfileEntity>()
    this.forEach {
        gitProfileEntity.add(
            GitProfileEntity(
                id = it.id,
                name = it.name,
                language = it.language,
                htmlUrl = it.htmlUrl,
                createdAt = it.createdAt,
                pushedAt = it.pushedAt
            )
        )
    }
    return gitProfileEntity

}

