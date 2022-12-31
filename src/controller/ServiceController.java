/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import helper.FormValidator;
import javax.swing.JFrame;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Layanan;

/**
 *
 * @author ariqn
 */
public class ServiceController extends Controller {
    
    private static Connection conn;
    private static ArrayList<Layanan> listLayanan = new ArrayList<>();
    
    public static void index(DefaultTableModel tableModel) {
        loadLayanan(tableModel);
    }
    
    public static void store(JFrame jFrame, String serviceName, String price) {
        conn = kumbahDB(jFrame);
        FormValidator validator = new FormValidator();
        
        boolean[] validated = {
            validator.patternMatches(serviceName, "[a-zA-Z\\s]{3,255}"),
            validator.patternMatches(price, "\\d{3,}")
        };
        
        String message = "";
        if (!validated[0]) {
            message = "Service name contains only letters, minimum 3 characters long and maximum 255 characters long";
            validator.validation(jFrame, message);
        } else if (!validated[1]) {
            message = "Price contains only number with a minimum of 3 digits";
            validator.validation(jFrame, message);
        } else {
            try {
                String insertQuery = "INSERT INTO layanan(nama, harga) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery);
                ps.setString(1, serviceName);
                ps.setLong(2, Long.parseLong(price));
                
                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(jFrame,
                            "Servcie added",
                            "Kumbah - Add service",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public static void update(JFrame jFrame, int id, String serviceName, String price, DefaultTableModel tableModel) {
        FormValidator validator = new FormValidator();
        
        boolean[] validated = {
            validator.patternMatches(serviceName, "[a-zA-Z\\s]{3,255}"),
            validator.patternMatches(price, "\\d{3,}")
        };
        
        String message = "";
        if (!validated[0]) {
            message = "Service name contains only letters, minimum 3 characters long and maximum 255 characters long";
            validator.validation(jFrame, message);
        } else if (!validated[1]) {
            message = "Price contains only number with a minimum of 3 digits";
            validator.validation(jFrame, message);
        } else {
            conn = kumbahDB(jFrame);
            
            try {
                String queryUpdate = "UPDATE layanan SET nama=?, harga=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(queryUpdate);
                ps.setString(1, serviceName);
                ps.setLong(2, Long.parseLong(price));
                ps.setInt(3, id);
                
                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    JOptionPane.showMessageDialog(jFrame,
                            "Servcie edited",
                            "Kumbah - Service",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                
                refresh(tableModel);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(jFrame,
                        "Failed to update! please check your connection!",
                        "Kumbah - Service", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void refresh(DefaultTableModel tableModel) {
        listLayanan.clear();
        tableModel.setRowCount(0);
        tableModel.fireTableRowsDeleted(0, listLayanan.size());
        loadLayanan(tableModel);
    }
    
    private static void loadLayanan(DefaultTableModel tableModel) {
        conn = kumbahDB();
        
        try {
            String query = "SELECT * FROM layanan";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String serviceName = rs.getString("nama");
                long price = rs.getLong("harga");
                
                listLayanan.add(new Layanan(id, serviceName, price));
                int index = listLayanan.size() - 1;
                
                tableModel.addRow(new Object[]{
                    listLayanan.get(index).getId(),
                    listLayanan.get(index).getNama(),
                    listLayanan.get(index).getHarga(),});
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public static ArrayList<Layanan> getListLayanan() {
        return listLayanan;
    }
}
