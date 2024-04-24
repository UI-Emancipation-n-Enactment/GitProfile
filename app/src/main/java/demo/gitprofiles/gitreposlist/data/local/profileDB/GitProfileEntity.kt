package demo.gitprofiles.gitreposlist.data.local.profileDB

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName="profiledetails_table")
data class GitProfileEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val language: String,
    val htmlUrl: String,
    val createdAt: String,
    val pushedAt: String
)
