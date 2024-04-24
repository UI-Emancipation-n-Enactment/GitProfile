package demo.gitprofiles.gitreposlist.data.local.profileDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GitProfileEntity::class], version=1)
abstract class GitProfileDetailsDatabase : RoomDatabase() {
   abstract fun gitProfileDAO(): GitProfileDAO
}