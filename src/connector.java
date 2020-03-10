

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connector {
    private static final String url = "jdbc:postgresql://db4parm-do-user-7150737-0.db.ondigitalocean.com:25060/defaultdb?sslmode=require";
    private static final String user = "doadmin";
    private static final String password = "de53jzgsny7bamuk";
    public static Connection connection = null;

    public void connect(String query) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url,user,password);

            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();

            System.out.println("Executing query...");
            ResultSet resultSet = statement.executeQuery(query );
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) +
                        "\n" + resultSet.getString(2) +
                        "\n" + resultSet.getString(3) +
                        "\n" + resultSet.getString(4) +
                        "\n" );
            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }



}
