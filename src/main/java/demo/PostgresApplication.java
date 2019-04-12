package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresApplication {

       public static void main(String[] args) {
             try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.7:5432/postgres",
                           "postgres", "docker")) {
                    System.out.println("Java JDBC PostgreSQL Example");
                    System.out.println("Connected to PostgreSQL database!");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select current_user;");
                    while (resultSet.next()) {
                           System.out.printf("%10s", resultSet.getString("current_user"));
                    }

             } catch (SQLException e) {
                    System.out.println("Connection failure.");
                    e.printStackTrace();
             }
       }
}

