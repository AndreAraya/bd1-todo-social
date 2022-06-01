package tec.bd.todo.auth.repository;

import tec.bd.todo.auth.ClientCredentials;
import tec.bd.todo.auth.datasource.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl extends BaseRepository<ClientCredentials> implements ClientRepository {

    private static String FIND_ALL_CLIENTS_QUERY = "select client_id,'[SECRET]' as client_secret from clients order by client_id asc";
    private static String FIND_BY_CLIENTID_QUERY = "select client_id, client_secret from clients where client_id = ?";
    private static String DELETE_CLIENT_QUERY = "delete from clients where client_id = ?";
    private static String SAVE_CLIENT_QUERY = "insert into clients(client_id, client_secret) values (?, ?)";

    public ClientRepositoryImpl(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    public List<ClientCredentials> findAll() {
        var clientList = new ArrayList<ClientCredentials>();

        try {
            var resultSet = this.query(FIND_ALL_CLIENTS_QUERY);
            while(resultSet.next()) {
                var client = toEntity(resultSet);
                if(null != client) {
                    clientList.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientList;
    }

    @Override
    public ClientCredentials findByClientId(String clientId) {
        //var clientList = new ArrayList<ClientCredentials>();

        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(FIND_BY_CLIENTID_QUERY);
            statement.setString(1, clientId);
            var resultSet = this.query(statement);
            while(resultSet.next()) {
                var client = toEntity(resultSet);
                return client;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ClientCredentials save(ClientCredentials clientCredentials) {
        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(SAVE_CLIENT_QUERY);
            statement.setString(1, clientCredentials.getClientId());
            statement.setString(2, clientCredentials.getClientSecret());

            var actual = this.execute(statement);

            return clientCredentials;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String clientId) {
        try {
            var connect = this.connect();
            var statement = connect.prepareStatement(DELETE_CLIENT_QUERY);
            statement.setString(1, clientId);
            var actual = this.execute(statement);
            System.out.println("Actual: " + actual);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClientCredentials toEntity(ResultSet resultSet) {
        try {
            var client = new ClientCredentials(
                    resultSet.getString("client_id"),
                    resultSet.getString("client_secret")
            );

            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
