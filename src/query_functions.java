

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

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
            connector connection = new connector();
            String myquery = "select ans.business_id, bb.name, bb.review_count, stars, ceu from (select city_business.business_id, count(*) ceu\n" +
                    "                  from (select business_id, review.user_id from review\n" +
                    "                      inner join elite_users a\n" +
                    "                          on a.user_id = review.user_id and a.elite = review.year) elite_reviews\n" +
                    "                     inner join (select * from business where city = '" + cityInput + "') city_business\n" +
                    "                          on city_business.business_id = elite_reviews.business_id\n" +
                    "group by city_business.business_id\n" +
                    "having count(*) > " + eliteCount + "\n" +
                    "order by count(*) desc\n" +
                    "LIMIT " + topCount + ") ans INNER JOIN (select * from business where city = '" + cityInput + "') bb on bb.business_id = ans.business_id;";
            connection.connect(myquery);
    }

    public static void main(String[] args) throws SQLException, IOException {
        query_functions functions = new query_functions();
        Scanner selector;
        Scanner input;
        int goThere;
        loop: while (true) {
            System.out.println("Input a number to select a function\n" +
                    "1: Still In Business\n" +
                    "2: Top Reviews\n" +
                    "3: Average Rating\n" +
                    "4: Top Business in City\n" +
                    "5: Quit");
            selector = new Scanner(System.in);
            if (selector.hasNextInt()) {
                goThere = selector.nextInt();
                dance: while (true) {
                    switch (goThere) {
                        case 1:
                            System.out.println("Input a state code in 2 character - e.g., AZ");
                            input = new Scanner(System.in);
                            String state = input.nextLine();
                            if (state.length() == 2) {
                                functions.stillThere(state);
                                break dance;
                            } else {
                                goThere = 1;
                                continue;
                            }
                        case 2:
                            System.out.println("Input a business id");
                            input = new Scanner(System.in);
                            String business_id = input.nextLine();
                            if (business_id.length() == 22) {
                                functions.topReviews(business_id);
                                break dance;
                            } else {
                                goThere = 2;
                                continue;
                            }
                        case 3:
                            System.out.println("Input a user id");
                            input = new Scanner(System.in);
                            String user_id = input.nextLine();
                            if (user_id.length() == 22) {
                                functions.averageRating(user_id);
                                break dance;
                            } else {
                                goThere = 3;
                                continue;
                            }
                        case 4:
                            System.out.println("Input a city name, elite count, and top count");
                            input = new Scanner(System.in);
                            String city_name = input.next();
                            int elite_count = input.nextInt();
                            int top_count = input.nextInt();
                            if (elite_count >= 0 && top_count > 0) {
                                functions.topBusinessInCity(city_name, elite_count, top_count);
                                break dance;
                            } else {
                                goThere = 4;
                                continue;
                            }
                        case 5:
                            break loop;
                        default:
                            System.out.println("Wrong number, please try again.");
                            System.out.println();
                            break;
                    }
                }
            }
        }
    }
}

