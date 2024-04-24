package demo.gitprofiles.di.module

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import demo.gitprofiles.BuildConfig.BASE_URL
import demo.gitprofiles.di.GithubReposService
import demo.gitprofiles.gitreposlist.data.local.profileDB.GitProfileDetailsDatabase
import demo.gitprofiles.gitreposlist.data.network.GithubreposApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GitRepositoryModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor = interceptor)
        .build()

    @Provides
    fun providesGithubReposApi(): GithubreposApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(GithubreposApi::class.java)
    }

    @Provides
    fun providesMoviesService(context: Context): GithubReposService {
        return GithubReposService(context)
    }

    @Singleton
    @Provides
    fun providesGitProfileDatabase(context: Context): GitProfileDetailsDatabase {
        return Room.databaseBuilder (
                context,
                GitProfileDetailsDatabase::class.java, "GitProfile.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}


