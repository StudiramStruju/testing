package io.test.postgres.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * NOT REALLY WORKING
 * FIXME -> Or maybe not... flyway handles these things
 */
@Service
public class DBUtil {

    public static final Logger log = LoggerFactory.getLogger(DBUtil.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBUtil(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Connection getConnection() throws SQLException {
        return Objects.requireNonNull(this.jdbcTemplate.getJdbcTemplate().getDataSource()).getConnection();
    }

    private Statement createStatement() throws SQLException {
        return this.getConnection().createStatement();
    }

    public void truncateDatabase() {
        this.listTables().forEach(this::truncateTable);
    }

    public List<Table> listTables() {
        return this.jdbcTemplate.query(
                "SELECT * FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';",
                (rs, rNum) -> new Table(rs.getString("schemaname"), rs.getString("tablename")));
    }

    public void truncateTable(final Table table) {
        try (final Statement statement = this.getConnection().createStatement()) {
            final int result = statement.executeUpdate(
                    "SET CONSTRAINTS ALL DEFERRED; TRUNCATE " + table.getFullName() + "; SET CONSTRAINTS ALL IMMEDIATE;");
            log.info(String.format(
                    "Truncating '%s' table %s", table.getFullName(), result == 1 ? "SUCCESS" : "FAILED"));
        } catch (final SQLException e) {
            log.error("Failed to truncate the DB!", e);
            throw new RuntimeException(e);
        }
    }

    public static class Table {
        private final String schema;
        private final String name;

        public Table(final String schema, final String name) {
            this.schema = schema;
            this.name = name;
        }

        public String getFullName() {
            return this.schema + "." + this.name;
        }

    }
}
