package slayer;

public class ProductoFactura {
    private String numeroFactura;
    private String codigoProducto;
    private double cantidad;
    private double valorUnitario;
    private double subtotal;
    private String estado;

    // Constructor, getters y setters

    public ProductoFactura(String numeroFactura, String codigoProducto, double cantidad, double valorUnitario, double subtotal, String estado) {
        this.numeroFactura = numeroFactura;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
        this.estado = estado;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

