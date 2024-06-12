package slayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificarDatos {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = Conexion.getConnection();

            // Verificar Factura
            String sqlFactura = "SELECT * FROM FACTURAS WHERE FACNUMERO = ?";
            PreparedStatement psFactura = connection.prepareStatement(sqlFactura);
            psFactura.setString(1, "F00000003");
            ResultSet rsFactura = psFactura.executeQuery();
            while (rsFactura.next()) {
                System.out.println("Factura No: " + rsFactura.getString("FACNUMERO"));
                System.out.println("Cliente: " + rsFactura.getString("CLICODIGO"));
                System.out.println("Fecha: " + rsFactura.getDate("FACFECHA"));
                System.out.println("Subtotal: " + rsFactura.getDouble("FACSUBTOTAL"));
                System.out.println("Descuento: " + rsFactura.getDouble("FACDESCUENTO"));
                System.out.println("IVA: " + rsFactura.getDouble("FACIVA"));
                System.out.println("Forma de Pago: " + rsFactura.getString("FACFORMAPAGO"));
                System.out.println("Status: " + rsFactura.getString("FACSTATUS"));
                System.out.println("------------------------------");
            }

            // Verificar Productos en Factura
            String sqlProductosFactura = "SELECT * FROM PXF WHERE FACNUMERO = ?";
            PreparedStatement psProductosFactura = connection.prepareStatement(sqlProductosFactura);
            psProductosFactura.setString(1, "F00000003");
            ResultSet rsProductosFactura = psProductosFactura.executeQuery();
            while (rsProductosFactura.next()) {
                System.out.println("Factura No: " + rsProductosFactura.getString("FACNUMERO"));
                System.out.println("Producto: " + rsProductosFactura.getString("PROCODIGO"));
                System.out.println("Cantidad: " + rsProductosFactura.getDouble("PXFCANTIDAD"));
                System.out.println("Valor: " + rsProductosFactura.getDouble("PXFVALOR"));
                System.out.println("Subtotal: " + rsProductosFactura.getDouble("PXFSUBTOTAL"));
                System.out.println("Status: " + rsProductosFactura.getString("PXFSTATUS"));
                System.out.println("------------------------------");
            }

            // Verificar Cliente
            String sqlCliente = "SELECT * FROM CLIENTES WHERE CLICODIGO = ?";
            PreparedStatement psCliente = connection.prepareStatement(sqlCliente);
            psCliente.setString(1, "C000002");
            ResultSet rsCliente = psCliente.executeQuery();
            while (rsCliente.next()) {
                System.out.println("Cliente Codigo: " + rsCliente.getString("CLICODIGO"));
                System.out.println("Nombre: " + rsCliente.getString("CLINOMBRE"));
                System.out.println("Identificación: " + rsCliente.getString("CLIIDENTIFICACION"));
                System.out.println("Dirección: " + rsCliente.getString("CLIDIRECCION"));
                System.out.println("Teléfono: " + rsCliente.getString("CLITELEFONO"));
                System.out.println("Celular: " + rsCliente.getString("CLICELULAR"));
                System.out.println("Email: " + rsCliente.getString("CLIEMAIL"));
                System.out.println("Tipo: " + rsCliente.getString("CLITIPO"));
                System.out.println("Status: " + rsCliente.getString("CLISTATUS"));
                System.out.println("------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
