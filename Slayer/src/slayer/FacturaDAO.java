package slayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaDAO {

    public void insertarFactura(Factura factura) {
        String sql = "INSERT INTO FACTURAS (FACNUMERO, CLICODIGO, FACFECHA, FACSUBTOTAL, FACDESCUENTO, FACIVA, FACICE, FACFORMAPAGO, FACSTATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, factura.getNumero());
            pstmt.setString(2, factura.getCodigoCliente());
            pstmt.setDate(3, factura.getFecha());
            pstmt.setDouble(4, factura.getSubtotal());
            pstmt.setDouble(5, factura.getDescuento());
            pstmt.setDouble(6, factura.getIva());
            pstmt.setDouble(7, factura.getIce());
            pstmt.setString(8, factura.getFormaPago());
            pstmt.setString(9, factura.getEstado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarProductoFactura(ProductoFactura productoFactura) {
        String sql = "INSERT INTO PXF (FACNUMERO, PROCODIGO, PXFCANTIDAD, PXFVALOR, PXFSUBTOTAL, PXFSTATUS) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productoFactura.getNumeroFactura());
            pstmt.setString(2, productoFactura.getCodigoProducto());
            pstmt.setDouble(3, productoFactura.getCantidad());
            pstmt.setDouble(4, productoFactura.getValorUnitario());
            pstmt.setDouble(5, productoFactura.getSubtotal());
            pstmt.setString(6, productoFactura.getEstado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
