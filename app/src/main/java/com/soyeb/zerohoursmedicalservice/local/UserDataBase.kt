package com.soyeb.zerohoursmedicalservice.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.soyeb.zerohoursmedicalservice.local.UserDAO
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soyeb.zerohoursmedicalservice.local.UserDataBase
import androidx.room.Room

@Database(entities = [User::class], version = 4)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDAO?

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE 'user' ADD COLUMN 'checked_val' INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE 'user' ADD COLUMN 'pro_image' TEXT"
                )
            }
        }
        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE 'user' ADD COLUMN 'device_uuid' TEXT"
                )
            }
        }
        private var dataBase: UserDataBase? = null
        fun getDataBase(context: Context?): UserDataBase? {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context!!, UserDataBase::class.java, "UserDb")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()
            }
            return dataBase
        }
    }
}