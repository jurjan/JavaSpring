package com.coursework.courseprojectfxeng.databaseControllers;

import com.coursework.courseprojectfxeng.model.Plant;
import com.coursework.courseprojectfxeng.model.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JDBCOperations {

    public static Connection connectToDb() {
        Connection connection = null;
        String DB_URL = "jdbc:mysql://localhost/lt_group_theory";
        String USER = "root";
        String PASS = "";
        try {
            Class.forName("com.mysql.jdbc.cj.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static List<Product> getProductList() throws SQLException {
        List<Product> list = new ArrayList<>();
        Connection connection = connectToDb();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM product";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String desc = resultSet.getString("description");
            int qty = resultSet.getInt(4);
            float weight = resultSet.getFloat(5);
            LocalDate date = resultSet.getDate(6).toLocalDate();

            Product product = new Plant(id, title, desc, qty, weight, date);
            list.add(product);
        }
        return list;
    }
}
