/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pamun
 */
public class OrderDetail
{
    protected int orderId, idLayanan;
    protected long totalHarga;
    protected int kuantitas;

    public OrderDetail(int orderId, int idLayanan, long totalHarga, int kuantitas)
    {
        this.orderId = orderId;
        this.idLayanan = idLayanan;
        this.totalHarga = totalHarga;
        this.kuantitas = kuantitas;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public int getIdLayanan()
    {
        return idLayanan;
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

    public void setIdLayanan(int idLayanan)
    {
        this.idLayanan = idLayanan;
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
