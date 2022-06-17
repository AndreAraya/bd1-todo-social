package tec.bd.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariDBManager implements DBManager {

    private DataSource dataSource;

    public HikariDBManager(HikariConfig hikariConfig) {
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        this.dataSource = ds;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
