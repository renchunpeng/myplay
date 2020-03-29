package com.cpren;

import org.junit.Test;

import java.sql.*;

public class SQLConnection {
    private Connection connection;

    public SQLConnection() throws SQLException {
        String url = "jdbc:mysql://192.168.1.143/v5_190630_aikn?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true";
        String user = "readonly";
        String password = "readonly";
        this.connection = DriverManager.getConnection(url, user, password);
    }

    @Test
    public void test() throws SQLException {
        String sql = "select * from kn_knowledge limit 10";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            System.out.println("知识ID：" + string);
        }
    }
}
