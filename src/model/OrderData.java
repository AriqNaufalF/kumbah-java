/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pamun
 */
public class OrderData
{
    protected int id;
    protected String tanggalTransaksi, tanggalSelesai;
    protected String customerName;
    protected String employeeName;
    protected String serviceName;
    protected long total;
    protected int quantity;

    public OrderData(int id, String tanggalTransaksi, String tanggalSelesai, String customerName, String employeeName, long total, int quantity, String serviceName)
    {
        this.id = id;
        this.tanggalTransaksi = tanggalTransaksi;
        this.tanggalSelesai = tanggalSelesai;
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.serviceName = serviceName;
        this.total = total;
        this.quantity = quantity;
    }
    
    public int getId()
    {
        return id;
    }

    public String getTanggalTransaksi()
    {
        return tanggalTransaksi;
    }

    public String getTanggalSelesai()
    {
        return tanggalSelesai;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public long getTotal()
    {
        return total;
    }

    public int getQuantity()
    {
        return quantity;
    }
}
