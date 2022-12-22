/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pamun
 */
public class Order
{
    protected int id, idKaryawan;
    protected String tanggalTransaksi, tanggalSelesai;
    protected String customerName;

    public Order(int id, int idKaryawan, String tanggalTransaksi, String tanggalSelesai, String customerName)
    {
        this.id = id;
        this.idKaryawan = idKaryawan;
        this.tanggalTransaksi = tanggalTransaksi;
        this.tanggalSelesai = tanggalSelesai;
        this.customerName = customerName;
    }

    public int getId()
    {
        return id;
    }

    public int getIdKaryawan()
    {
        return idKaryawan;
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

    public void setIdKaryawan(int idKaryawan)
    {
        this.idKaryawan = idKaryawan;
    }

    public void setTanggalTransaksi(String tanggalTransaksi)
    {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public void setTanggalSelesai(String tanggalSelesai)
    {
        this.tanggalSelesai = tanggalSelesai;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
}
