/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.password4j.Password;
import helper.FormValidator;
import javax.swing.JFrame;
import java.sql.*;
import java.util.Optional;
import javax.swing.JOptionPane;
import model.Karyawan;
import view.admin.Index;
import view.auth.Login;

/**
 *
 * @author ariqn
 */
public class LoginController extends Controller {

    public static void loginUser(JFrame parent, String email, String password) {
        FormValidator validator = new FormValidator();

        boolean[] validated = {validator.patternMatches(email, FormValidator.EMAIL_REGEX),
            validator.patternMatches(password, FormValidator.PASSWORD_REGEX)};

        String message;
        if (!validated[0]) {
            message = "Email format is invalid";
            validator.validation(parent, message);
        } else if (!validated[1]) {
            message = "Password is at least 8 characters long";
            validator.validation(parent, message);
        } else {
            queryKaryawan(parent, email, password);
        }
    }

    public static void logout(JFrame parent, Karyawan karyawan) {
        JOptionPane.showMessageDialog(parent, "Goodbye " + karyawan.getName() + "!");

        goToLogin(parent);
    }

    private static void queryKaryawan(JFrame parent, String email, String password) {
        Connection conn = kumbahDB(parent);
        String query = "SELECT * FROM karyawan WHERE email=?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            Karyawan user = null;
            while (rs.next()) {
                user = new Karyawan(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"));
            }

            Optional<Karyawan> userOptional = Optional.ofNullable(user);
            if (checkPassword(userOptional, password)) {
                JOptionPane.showMessageDialog(parent, "Welcome " + user.getName() + "!");
                Index index = new Index(user);

                parent.dispose();
                index.setSize(960, 540);
                index.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(parent, "These credentials do not match our records", "Login error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(parent, e.getMessage(), "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean checkPassword(Optional<Karyawan> userOptional, String password) {
        boolean verified;
        String passwordFromDB = userOptional.map(Karyawan::getPassword).orElse("");
        verified = passwordFromDB.isEmpty()
                ? false
                : Password.check(password, passwordFromDB)
                        .addPepper("kumbah").withBcrypt();

        return verified;
    }

    private static void goToLogin(JFrame jFrame) {
        Login login = new Login();

        jFrame.dispose();
        login.setSize(960, 540);
        login.setVisible(true);
    }
}
