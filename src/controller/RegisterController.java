package controller;

import helper.FormValidator;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.JOptionPane;
import view.auth.Login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ariqn
 */
public class RegisterController extends Controller {

    public static void registerUser(JFrame jFrame, String name, String email, String password) {
        Connection conn = kumbahDB(jFrame);
        FormValidator validator = new FormValidator();

        boolean[] validated = { validator.patternMatches(name, "[a-zA-Z\\.\\s]{3,255}"),
                validator.patternMatches(email, FormValidator.EMAIL_REGEX),
                validator.patternMatches(password, FormValidator.PASSWORD_REGEX) };
        String message = "";
        if (!validated[0]) {
            message = "Name contains only letters, minimum 3 characters long and maximum 255 characters long";
            validator.validation(jFrame, message);
        } else if (!validated[1]) {
            message = "Email format is invalid";
            validator.validation(jFrame, message);

        } else if (!validated[2]) {
            message = "Password is at least 8 characters long";
            validator.validation(jFrame, message);
        } else {
            try {
                String insertQuery = "INSERT INTO karyawan(name, email, password) VALUES(?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("Register successfull");
                    JOptionPane.showMessageDialog(jFrame,
                            "Register successfull, please login!",
                            "Regsiter", JOptionPane.INFORMATION_MESSAGE);
                    goToLogin(jFrame);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void goToLogin(JFrame jFrame) {
        Login login = new Login();

        jFrame.dispose();
        login.setSize(960, 540);
        login.setVisible(true);
    }
}
