package demo.gitprofiles.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import demo.gitprofiles.gitreposlist.data.local.profileDB.GitProfileDetailsDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesGitProfileDatabase(
        context: Context
    ): GitProfileDetailsDatabase {
        return Room.databaseBuilder (
            context,
            GitProfileDetailsDatabase::class.java, "GitProfile.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}