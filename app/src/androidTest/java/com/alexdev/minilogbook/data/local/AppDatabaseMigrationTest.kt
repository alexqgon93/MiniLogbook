package com.alexdev.minilogbook.data.local

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseMigrationTest {
    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(
            instrumentation = InstrumentationRegistry.getInstrumentation(),
            databaseClass = AppDataBase::class.java,
        )

    @Test
    fun version1Schema_validatesAndPreservesExistingReadings() {
        helper
            .createDatabase(TEST_DB, 1)
            .apply {
                insertReading(id = 1L, valueInMmol = 5.5, timestamp = 1_717_000_000_000L)
                close()
            }

        helper
            .runMigrationsAndValidate(
                name = TEST_DB,
                version = 1,
                validateDroppedTables = true,
                migrations = DatabaseMigrations.ALL,
            ).apply {
                query("SELECT id, valueInMmol, timestamp FROM bg_entry WHERE id = 1").use { cursor ->
                    check(cursor.moveToFirst())
                    check(cursor.getLong(0) == 1L)
                    check(cursor.getDouble(1) == 5.5)
                    check(cursor.getLong(2) == 1_717_000_000_000L)
                }
                close()
            }
    }

    private fun SupportSQLiteDatabase.insertReading(
        id: Long,
        valueInMmol: Double,
        timestamp: Long,
    ) {
        execSQL(
            "INSERT INTO bg_entry (id, valueInMmol, timestamp) VALUES ($id, $valueInMmol, $timestamp)",
        )
    }

    private companion object {
        const val TEST_DB = "migration-test"
    }
}
