/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pamun
 */
public class OrderDetail extends Order
{
    protected int orderId;
    protected long totalHarga;
    protected int kuantitas;

    public OrderDetail(int orderId, long totalHarga, int kuantitas, int id, int idKaryawan, int idLayanan, String tanggalTransaksi, String tanggalSelesai, String customerName)
    {
        super(id, idKaryawan, idLayanan, tanggalTransaksi, tanggalSelesai, customerName);
        this.orderId = orderId;
        this.totalHarga = totalHarga;
        this.kuantitas = kuantitas;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public long getTotalHarga()
    {
        return totalHarga;
    }

    public int getKuantitas()
    {
        return kuantitas;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public void setTotalHarga(long totalHarga)
    {
        this.totalHarga = totalHarga;
    }

    public void setKuantitas(int kuantitas)
    {
        this.kuantitas = kuantitas;
    }
}
