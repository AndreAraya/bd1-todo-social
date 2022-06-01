package tec.bd.todo.auth.repository;

import tec.bd.todo.auth.datasource.DBManager;

import java.sql.*;

public abstract class BaseRepository<T> {

    protected DBManager dbManager;

    protected BaseRepository(DBManager dbManager) {
        this.dbManager = dbManager;
    }


    protected Connection connect() throws SQLException {
        return this.dbManager.getConnection();
    }

    protected ResultSet query(String sqlQuery) throws SQLException {
        //Connection connection = DriverManager.getConnection(CONNECTION_STRING, DB_USERNAME, DB_PASSWORD);
        var connection = this.connect();
        Statement stmt = connection.createStatement();
        //execute query -- consultas de seleccion
        ResultSet resultSet = stmt.executeQuery(sqlQuery);
        return resultSet;
    }

    protected ResultSet query(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    // TODO: Crear metodo execute(String sqlQuery)

    // TODO: Crear metodo execute(PreparedStatement stmt)
    protected boolean execute(PreparedStatement stmt) throws SQLException {
        return stmt.execute();
    }



    public abstract T toEntity(ResultSet resultSet);

}
