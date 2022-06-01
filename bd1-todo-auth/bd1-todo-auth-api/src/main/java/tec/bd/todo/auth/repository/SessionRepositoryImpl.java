package tec.bd.todo.auth.repository;

import tec.bd.todo.auth.ClientCredentials;
import tec.bd.todo.auth.Session;
import tec.bd.todo.auth.SessionStatus;
import tec.bd.todo.auth.datasource.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SessionRepositoryImpl extends BaseRepository<Session> implements SessionRepository {


    private static String FIND_ALL_SESSION_QUERY = "select client_id,session_id,createdat, if(minute(timediff(utc_timestamp(), createdat)) <= 30, \"ACTIVE\", \"INACTIVE\" ) as sessionStatus from sessions order by createdat asc";
    private static String VALIDATE_SESSION_QUERY = "validate_session(?)";
    private static String CREATE_SESSION_QUERY = "create_session(?)";
    private static String UPDATE_SESSION_QUERY = "update sessions set session_id = ?, createdat = ? where client_id = ?";


    public SessionRepositoryImpl(DBManager dbManager) {
        super(dbManager);
    }


    @Override
    public List<Session> findAll() {
        var sessions = new ArrayList<Session>();

        try {
            var resultSet = this.query(FIND_ALL_SESSION_QUERY);
            while(resultSet.next()) {
                var client = toEntity(resultSet);
                if(null != client) {
                    sessions.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }

    public Session validate(String sessionId) {

        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(VALIDATE_SESSION_QUERY);
            statement.setString(1, sessionId);
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
    public Session create(Session session) {
        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(CREATE_SESSION_QUERY);
            statement.setString(1, session.getClientId());

            var actual = this.execute(statement);

            // Si la session se actualizó exitosamente entonces se pone el estatus

            session.setStatus(SessionStatus.ACTIVE);

            return session;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Session update(Session session) {
        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(UPDATE_SESSION_QUERY);
            statement.setString(1, session.getClientId());
            statement.setString(2, session.getSessionId());
            statement.setTimestamp(3, new Timestamp(session.getCreatedAt().getTime()));

            var actual = this.execute(statement);

            // Si la session se guardó exitosamente entonces se pone el estatus

            session.setStatus(SessionStatus.ACTIVE);

            return session;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Session toEntity(ResultSet resultSet) {
        try {
            var session = new Session(
                    resultSet.getString("client_id"),
                    resultSet.getString("session_id"),
                    SessionStatus.valueOf(resultSet.getString("sessionStatus").toUpperCase()),
                    resultSet.getDate("createdat")
            );

            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
