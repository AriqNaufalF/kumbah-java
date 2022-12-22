/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pamun
 */
public class Layanan
{
    protected int id;
    protected String nama;
    protected long harga;

    public Layanan(int id, String nama, long harga)
    {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public int getId()
    {
        return id;
    }

    public String getNama()
    {
        return nama;
    }

    public long getHarga()
    {
        return harga;
    }

    public void setNama(String nama)
    {
        this.nama = nama;
    }

    public void setHarga(long harga)
    {
        this.harga = harga;
    }
}
