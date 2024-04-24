package demo.gitprofiles.gitreposlist.data.local.profileDB

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GitProfileDAO {

    @Upsert
    suspend fun upsertGitProfile(gitProfileEntity: List<GitProfileEntity>)

    @Query("Select * from profiledetails_table")         // returns all profiles
    suspend fun getGitProfiles(): GitProfileEntity

    @Query("Select * from profiledetails_table WHERE name = :name")
    suspend fun getGitProfileByName(name: String) : GitProfileEntity

    @Query("Delete from profiledetails_table")           // deletes all profiles
    suspend fun deleteAllProfiles()
}
