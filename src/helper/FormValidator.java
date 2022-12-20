/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.awt.Component;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author ariqn
 */
public class FormValidator {

    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_REGEX = "[\\w\\W]{8,}";

    public void validation(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Input error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean patternMatches(String value, String regex) {
        return Pattern.compile(regex).matcher(value).matches();
    }
}
