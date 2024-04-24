package demo.gitprofiles.gitreposlist.data.local.profileDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="profiledetails_table")
data class GitProfileEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val Name: String,
    val Language: String,
    val Url: String,
    val Created: String,
    val Updated: String
)
