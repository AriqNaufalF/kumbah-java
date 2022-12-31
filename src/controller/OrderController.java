/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import helper.FormValidator;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Karyawan;
import model.Layanan;
import model.Order;
import model.OrderData;

/**
 *
 * @author pamun
 */
public class OrderController extends Controller
{
    private static Connection conn;
    private static ArrayList<Order> listOrder = new ArrayList<>();
    private static ArrayList<OrderData> listOrderData = new ArrayList<>();
    private static ArrayList<Layanan> listLayanan = new ArrayList<>();
    
    public static void index(DefaultTableModel tableModel)
    {
        refresh(tableModel);
    }
    
    private static ArrayList<Layanan> loadListLayanan()
    {   
        listLayanan.clear();
        conn = kumbahDB();
        
        try
        {
            String query = "SELECT * FROM layanan";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String serviceName = rs.getString("nama");
                long price = rs.getLong("harga");
                
                listLayanan.add(new Layanan(id, serviceName, price));
            }
            
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return listLayanan;
    }
    
    public static void populateList(JComboBox component)
    {
        component.removeAllItems();
        
        loadListLayanan().forEach(layanan -> {
            component.addItem(layanan.getNama());
        });
    }
    
    public static void store(JFrame jFrame, String namaPelanggan, String beratCucian, int idLayanan, Karyawan karyawan)
    {
        conn = kumbahDB(jFrame);
        FormValidator validator = new FormValidator();
        
        boolean[] validated = {
            validator.patternMatches(namaPelanggan, "[a-zA-Z\\s]{3,255}"),
            validator.patternMatches(beratCucian, "\\d{1,}"),
        };
        
        String message = "";
        if (!validated[0])
        {
            message = "Customer name contains only letters, minimum 3 characters long and maximum 255 characters long";
            validator.validation(jFrame, message);
        }
        else if (!validated[1]) 
        {
            message = "Weight contains only number with a minimum of 1 digits";
            validator.validation(jFrame, message);
        }
        else 
        {
            try 
            {
                String orderQuery = "INSERT INTO orders("
                        + "tanggal_transaksi,"
                        + "id_karyawan,"
                        + "nama_pelanggan,"
                        + "id_layanan)"
                        + "VALUES (?, ?, ?, ?)";
                PreparedStatement orderPs = conn.prepareStatement(orderQuery);
                
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String now = LocalDate.now().format(df);
                Date tanggalTransaksi = Date.valueOf(now);
                
                orderPs.setDate(1, tanggalTransaksi);
                orderPs.setInt(2, karyawan.getId());
                orderPs.setString(3, namaPelanggan);
                orderPs.setInt(4, idLayanan);
                
                int orderTableAffected = orderPs.executeUpdate();
                
                String cariHargaLayanan = "SELECT harga FROM layanan WHERE id = ?";
                PreparedStatement psHarga = conn.prepareStatement(cariHargaLayanan);
                psHarga.setInt(1, idLayanan);
                ResultSet hargaSet = psHarga.executeQuery();
                
                long harga = 0;
                int berat = Integer.parseInt(beratCucian);
                while (hargaSet.next())
                {
                    harga = hargaSet.getLong("harga");
                }
                
                String orderDetailQuery = "INSERT INTO order_detail("
                        + "total_harga,"
                        + "berat_cucian)"
                        + "VALUES (?, ?)";
                PreparedStatement orderDetailPs = conn.prepareStatement(orderDetailQuery);
                orderDetailPs.setLong(1, (harga * berat));
                orderDetailPs.setInt(2, berat);
                
                int orderDetailTableAffected = orderDetailPs.executeUpdate();
                
                if (orderTableAffected > 0 && orderDetailTableAffected > 0) 
                {
                    JOptionPane.showMessageDialog(jFrame,
                            "Order added",
                            "Kumbah - Add order",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (SQLException e) 
            {
                System.err.println(e.getMessage());
            }
        }
        
    }
    
    public static void update(JFrame jFrame, int id, DefaultTableModel tableModel)
    {
        conn = kumbahDB(jFrame);
        
        try 
        {
            String checkQuery = "SELECT tanggal_selesai FROM orders WHERE id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, id);
            
            ResultSet checkSet = checkPs.executeQuery();
            String checkValue = "";
            while (checkSet.next())
            {
                checkValue = checkSet.getString("tanggal_selesai");
            }
            
            if (checkValue == null)
            {
                String orderQuery = "UPDATE orders SET tanggal_selesai = ? WHERE id = ?";
                PreparedStatement orderPs = conn.prepareStatement(orderQuery);

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String now = LocalDate.now().format(df);
                Date tanggalSelesai = Date.valueOf(now);

                orderPs.setDate(1, tanggalSelesai);
                orderPs.setInt(2, id);

                int orderTableAffected = orderPs.executeUpdate();

                if (orderTableAffected > 0) 
                {
                    JOptionPane.showMessageDialog(jFrame,
                            "Order finished",
                            "Kumbah - Finish order",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                
                refresh(tableModel);
            }
            else
            {
                JOptionPane.showMessageDialog(jFrame,
                        "Order Already Finished",
                        "Kumbah - Order", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(jFrame,
                        "Failed to update! please check your connection!",
                        "Kumbah - Order", JOptionPane.ERROR_MESSAGE);
            System.err.println(e.getMessage());
        }
    }
    
    public static void refresh(DefaultTableModel tableModel)
    {
        listOrder.clear();
        listOrderData.clear();
        
        tableModel.setRowCount(0);
        tableModel.fireTableRowsDeleted(0, listOrderData.size());
        loadOrder(tableModel);
    }
    
    private static void loadOrder(DefaultTableModel tableModel)
    {
        conn = kumbahDB();
        
        try
        {
            String query = "SELECT * FROM orders";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next())
            {
                int id = rs.getInt("id");
                String tanggalTransaksi = rs.getString("tanggal_transaksi");
                String tanggalSelesai = rs.getString("tanggal_selesai");
                String namaPelanggan = rs.getString("nama_pelanggan");
                int idKaryawan = rs.getInt("id_karyawan");
                int idLayanan = rs.getInt("id_layanan");
                
                listOrder.add(new Order(id, idKaryawan, idLayanan, tanggalTransaksi, tanggalSelesai, namaPelanggan));
            }
            
            listOrder.forEach(it -> {
                try
                {
                    String getData = "SELECT "
                            + "karyawan.name as nama_karyawan,"
                            + "layanan.nama as nama_layanan,"
                            + "order_detail.total_harga,"
                            + "order_detail.berat_cucian "
                            + "FROM karyawan, layanan, order_detail "
                            + "WHERE karyawan.id = ? AND layanan.id = ? AND order_detail.id = ?";
                    
                    PreparedStatement ps = conn.prepareStatement(getData);
                    ps.setInt(1, it.getIdKaryawan());
                    ps.setInt(2, it.getIdLayanan());
                    ps.setInt(3, it.getId());
                    
                    ResultSet dataSet = ps.executeQuery();
                    
                    while (dataSet.next())
                    {
                        String namaKaryawan = dataSet.getString("nama_karyawan");
                        String namaLayanan = dataSet.getString("nama_layanan");
                        long totalHarga = dataSet.getInt("total_harga");
                        int beratCucian = dataSet.getInt("berat_cucian");
                        
                        listOrderData.add(new OrderData(it.getId(),
                                                        it.getTanggalTransaksi(),
                                                        it.getTanggalSelesai(),
                                                        it.getCustomerName(),
                                                        namaKaryawan,
                                                        totalHarga,
                                                        beratCucian,
                                                        namaLayanan));
                        
                        int index = listOrderData.size() - 1;
                        
                        tableModel.addRow(new Object[]{
                            listOrderData.get(index).getTanggalTransaksi(),
                            listOrderData.get(index).getTanggalSelesai(),
                            listOrderData.get(index).getCustomerName(),
                            listOrderData.get(index).getEmployeeName(),
                            listOrderData.get(index).getTotal(),
                            listOrderData.get(index).getQuantity(),
                            listOrderData.get(index).getServiceName()
                        });
                    }
                } 
                catch (SQLException e)
                {
                    System.err.println("Order Detail : " + e.getMessage());
                }
            });
        } 
        catch (SQLException e)
        {
            System.err.println("Order : " + e.getMessage());
        }
    }
    
    public static ArrayList<OrderData> getListOrderData() {
        return listOrderData;
    }
}
