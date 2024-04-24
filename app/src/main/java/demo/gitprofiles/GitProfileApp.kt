package demo.gitprofiles

import android.app.Application
import demo.gitprofiles.di.DaggerReposComponent
import demo.gitprofiles.di.ReposComponent

class GitProfileApp : Application() {

    lateinit var reposComponent: ReposComponent

    override fun onCreate() {
        super.onCreate()
        reposComponent = DaggerReposComponent.factory().create(this)
    }

}