package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;


    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();

        } catch (SQLException e) {
            System.out.println(e.getCause());
        }
    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String command = "CREATE TABLE USER (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR (30),lastName VARCHAR (30), age INT )";
            int result = statement.executeUpdate(command);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Данная таблица уже создана");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String command = "DROP TABLE IF EXISTS  USER";
            statement.executeUpdate(command);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USER (name,lastName, age) VALUES (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("User с именем "+ name+" добавлен в таблицу");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            String command = "DELETE FROM USER WHERE id=" + id;
            statement.executeUpdate(command);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list);
        return list;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String command = "DELETE FROM USER";
            statement.executeUpdate(command);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
