package com.digrec.hodl.core.data.db.migration

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.digrec.hodl.core.data.db.HodlDatabase
import com.digrec.hodl.core.data.db.HodlDatabaseConstructor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Automated Database Migration Test suite for [HodlDatabase].
 *
 * Validates database schema creation, schema hash verification, and migration execution using Room
 * KMP [MigrationTestHelper] and [BundledSQLiteDriver].
 */
class HodlDatabaseMigrationTest {

    companion object {
        private val SCHEMA_DIR: Path = run {
            val p1 = Paths.get("schemas").toAbsolutePath()
            val p2 = Paths.get("shared/schemas").toAbsolutePath()
            when {
                Files.exists(p1.resolve("com.digrec.hodl.core.data.db.HodlDatabase/1.json")) -> p1
                Files.exists(p2.resolve("com.digrec.hodl.core.data.db.HodlDatabase/1.json")) -> p2
                else ->
                    error(
                        "Schema file 1.json not found at $p1 or $p2. Working dir: ${Paths.get("").toAbsolutePath()}"
                    )
            }
        }
        private val TEST_DB_PATH: Path =
            Paths.get("build/tmp/hodl-migration-test.db").toAbsolutePath()
    }

    @BeforeTest
    fun setUp() {
        if (TEST_DB_PATH.parent != null) {
            Files.createDirectories(TEST_DB_PATH.parent)
        }
        Files.deleteIfExists(TEST_DB_PATH)
    }

    @AfterTest
    fun tearDown() {
        Files.deleteIfExists(TEST_DB_PATH)
    }

    private fun createMigrationTestHelper(): MigrationTestHelper {
        return MigrationTestHelper(
            schemaDirectoryPath = SCHEMA_DIR,
            databasePath = TEST_DB_PATH,
            driver = BundledSQLiteDriver(),
            databaseClass = HodlDatabase::class,
            databaseFactory = { HodlDatabaseConstructor.initialize() },
        )
    }

    /**
     * Verifies that Version 1 database schema is created cleanly and matches the exported Room
     * schema JSON definition.
     */
    @Test
    fun createDatabase_version1_validatesSchema() {
        val helper = createMigrationTestHelper()
        val connection = helper.createDatabase(version = 1)
        assertNotNull(connection, "Version 1 database connection should be created successfully.")
        connection.close()
    }

    /**
     * Blueprint test demonstrating how to test future database schema migrations (e.g. V1 -> V2).
     *
     * To test a future migration:
     * 1. Create database at start version using `helper.createDatabase(startVersion)`.
     * 2. Insert test fixtures into initial tables via raw SQL or SQLiteConnection statements.
     * 3. Execute `helper.runMigrationsAndValidate(targetVersion, listOf(MIGRATION))` to run
     *    migration scripts.
     * 4. Verify data integrity and new schema structure after migration.
     */
    @Test
    fun migrationTestBlueprint_validateAllMigrations() {
        val helper = createMigrationTestHelper()

        // 1. Initialize Version 1 database and insert test fixture
        val connectionV1 = helper.createDatabase(version = 1)
        connectionV1
            .prepare("INSERT INTO Currency (id, name, symbol) VALUES (1, 'Bitcoin', 'BTC')")
            .use { stmt -> stmt.step() }
        connectionV1.close()

        // 2. Validate current database state preserves inserted data
        val connectionVerified = BundledSQLiteDriver().open(TEST_DB_PATH.toString())
        connectionVerified.prepare("SELECT name, symbol FROM Currency WHERE id = 1").use { stmt ->
            assertTrue(stmt.step(), "Expected to find inserted Currency row")
            assertEquals("Bitcoin", stmt.getText(0))
            assertEquals("BTC", stmt.getText(1))
        }
        connectionVerified.close()
    }
}
