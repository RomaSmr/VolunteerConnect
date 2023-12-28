package com.example.demo.db;

import com.example.demo.models.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DbFunctions {
    public Connection connect_to_db() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "Request", "postgres", "123");
            if (connection != null) {
                System.out.println("Подключение успешно!");
            } else {
                System.out.println("Подключение безуспешно!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void registerUser(String last_name, String first_name, String middle_name, String login, String password, String role) {
        try {
            String query = String.format("insert into users(last_name, first_name, middle_name, login, password, role) values('%s', '%s', '%s', '%s', '%s', '%s');", last_name, first_name, middle_name, login, password, role);
            Statement statement = connect_to_db().createStatement();
            statement.executeUpdate(query);
            System.out.println("Пользователь успешно создан!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int checkLogin(String login) {
        try {
            String query = String.format("select * from users where login='%s'", login);
            Statement statement;
            statement = connect_to_db().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.last();
            if (resultSet.getRow() >= 1) {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 404;
        }
        return 201;
    }

    public int loginUser(String login, String password) {
        try {
            String query = String.format("select * from users where login='%s' and password='%s'", login, password);
            Statement statement = connect_to_db().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                return 0;
            }
            Variables.ACTIVE_USER_ID = resultSet.getString("id");
            Variables.ACTIVE_USER_LAST_NAME = resultSet.getString("last_name");
            Variables.ACTIVE_USER_FIRST_NAME = resultSet.getString("first_name");
            Variables.ACTIVE_USER_MIDDLE_NAME = resultSet.getString("middle_name");
            Variables.ACTIVE_USER_LOGIN = resultSet.getString("login");
            Variables.ACTIVE_USER_PASSWORD = resultSet.getString("password");
            Variables.ACTIVE_USER_ROLE = resultSet.getString("role");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 404;
        }
        return 201;
    }

    public ObservableList<Request> getAllRequests() {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = connect_to_db().createStatement().executeQuery("select * from requests");
            while (resultSet.next()) {
                requests.add(new Request(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date"),
                        resultSet.getString("status")
                ));
            }
            return requests;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return requests;
        }
    }

    public void createRequest(String name, String description, String start_date, String end_date, String status) {
        try {
            String query = String.format("insert into requests(name, description, start_date, end_date, status) values('%s', '%s', '%s', '%s', '%s');", name, description, start_date, end_date, status);
            Statement statement = connect_to_db().createStatement();
            statement.executeUpdate(query);
            System.out.println("Заявка успешно создана!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRequest(String id, String name, String description, String start_date, String end_date, String status) {
        try {
            String query = String.format("update requests set name='%s', description='%s', start_date='%s', end_date='%s', status='%s' where id='%s'", name, description, start_date, end_date, status, id);
            Statement statement = connect_to_db().createStatement();
            statement.executeUpdate(query);
            System.out.println("Данные успешно были обновлены!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRequest(String id) {
        try {
            String query = String.format("delete from requests where id='%s'", id);
            connect_to_db().createStatement().executeUpdate(query);
            System.out.println("Данные успешно были удалены!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int checkNameRequest(String name) {
        try {
            String query = String.format("select * from requests where name='%s'", name);
            Statement statement;
            statement = connect_to_db().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.last();
            if (resultSet.getRow() >= 1) {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 404;
        }
        return 201;
    }
}