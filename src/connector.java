

import java.sql.*;

public class connector {
    private static final String url = "jdbc:postgresql://db4parm-do-user-7150737-0.db.ondigitalocean.com:25060/defaultdb?sslmode=require";
    private static final String user = "doadmin";
    private static final String password = "de53jzgsny7bamuk";
    public static Connection connection = null;

    public void connect(String query) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url,user,password);

            Statement statement = connection.createStatement();

            System.out.println("Executing query...");
            ResultSet resultSet = statement.executeQuery(query );
            display(resultSet);

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void display(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i)+ ": " + resultSet.getString(i));
            }
            System.out.println();
        }
        System.out.println("Query completed\n");
    }



}
