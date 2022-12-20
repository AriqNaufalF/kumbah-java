/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Component;
import java.sql.*;

import javax.swing.JOptionPane;

/**
 *
 * @author ariqn
 */
public class Controller {

    protected static Connection kumbahDB() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kumbah_java", "root", "");
        } catch (SQLException e) {
            System.err.println("Database connection error" + e.getMessage());
        }
        return conn;
    }

    protected static Connection kumbahDB(Component parent) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kumbah_java", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage(), "Database connection error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }
}
