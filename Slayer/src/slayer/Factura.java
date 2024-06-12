package slayer;

import java.sql.Date;

public class Factura {
    private String numero;
    private String codigoCliente;
    private Date fecha;
    private double subtotal;
    private double descuento;
    private double iva;
    private double ice;
    private String formaPago;
    private String estado;

    // Constructor, getters y setters

    public Factura(String numero, String codigoCliente, Date fecha, double subtotal, double descuento, double iva, double ice, String formaPago, String estado) {
        this.numero = numero;
        this.codigoCliente = codigoCliente;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.iva = iva;
        this.ice = ice;
        this.formaPago = formaPago;
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getIce() {
        return ice;
    }

    public void setIce(double ice) {
        this.ice = ice;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
