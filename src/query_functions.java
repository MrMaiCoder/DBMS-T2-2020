

import java.sql.*;

public class query_functions {

    private static final String url = "jdbc:postgresql://db4parm-do-user-7150737-0.db.ondigitalocean.com:25060/defaultdb?sslmode=require";
    private static final String user = "doadmin";
    private static final String password = "de53jzgsny7bamuk";
    public static Connection connection = null;

    public void stillThere(String stateInput) throws SQLException {
        connector connection = new connector();
        String myquery = "SELECT business_id, name, address, stars " +
                "FROM business " +
                "WHERE (is_open = 1 AND state = " + "'" + stateInput + "'" + ") " +
                "ORDER BY review_count DESC " +
                "LIMIT 10 ";
        connection.connect(myquery);
    }

    public void topReviews(String businessIdInput) throws SQLException {
        connector connection = new connector();
        String myquery = "SELECT review.user_id, name, stars, text " +
                "FROM review, users " +
                "WHERE (review.user_id = users.user_id) AND (business_id = " + "'" + businessIdInput + "'" + ") " +
                "ORDER BY useful DESC " +
                "LIMIT 5 ";
        connection.connect(myquery);
    }
    public void averageRating(String userIdInput) throws SQLException {
        connector connection = new connector();
        String myquery = "SELECT user_id, name, average_stars " +
                "FROM users " +
                "WHERE user_id = " + "'" + userIdInput + "'";
        connection.connect(myquery);
    }
    public void topBusinessInCity(String cityInput, int eliteCount, int topCount) throws SQLException {


    }

    public static void main(String[] args) throws SQLException {
        new query_functions().stillThere("AZ");

    }

}
