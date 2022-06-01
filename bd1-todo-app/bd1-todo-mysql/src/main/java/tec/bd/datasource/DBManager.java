package tec.bd.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface DBManager {

    DataSource getDataSource();

    Connection getConnection() throws SQLException;

}
