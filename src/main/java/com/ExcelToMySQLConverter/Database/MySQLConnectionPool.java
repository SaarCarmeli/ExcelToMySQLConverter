package com.ExcelToMySQLConverter.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class MySQLConnectionPool {
    private static final int NUMBER_OF_CONNECTIONS = 10;
    private final Stack<Connection> connections = new Stack<>();
    private static MySQLConnectionPool instance = null;

    private MySQLConnectionPool() throws SQLException {
        openAllConnections();
    }

    public static MySQLConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (MySQLConnectionPool.class) {
                if (instance == null) {
                    instance = new MySQLConnectionPool();
                }
            }
        }
        return instance;
    }

    private void openAllConnections() throws SQLException {
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            Connection connection = DriverManager.getConnection(MySQLDBManager.SQL_URL, MySQLDBManager.SQL_USER, MySQLDBManager.SQL_PASSWORD);
            connections.push(connection);
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }
}
