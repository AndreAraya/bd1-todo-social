package tec.bd.social.repository;

import tec.bd.social.Rating;
import tec.bd.social.datasource.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingsRepositoryImpl extends BaseRepository<Rating> implements RatingsRepository {

    private static final String FIND_BY_RATING_ID_QUERY = "select id, todoid, ratingvalue, createdat, clientid from rating where id = ?"; //cambiar luego a procedimiento almacenado

    public RatingsRepositoryImpl(DBManager dbManager){
        super(dbManager);
    }

    @Override
    public Rating toEntity(ResultSet resultSet) {
        try {
            var rating = new Rating(
                    resultSet.getInt("id"),
                    resultSet.getInt("ratingvalue"),
                    resultSet.getString("todoid"),
                    resultSet.getString("clientid"),
                    resultSet.getDate("createdat")
            );
            return rating;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rating findById(int ratingId) {

        try {
            var connection = this.connect();
            var statement = connection.prepareStatement(FIND_BY_RATING_ID_QUERY);
            statement.setInt(1, ratingId);
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
}
