package slayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT PROCODIGO, PRODESCRIPCION, PROUNIDADMEDIDA FROM PRODUCTOS";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigo = rs.getString("PROCODIGO");
                String descripcion = rs.getString("PRODESCRIPCION");
                String unidadMedida = rs.getString("PROUNIDADMEDIDA");
                productos.add(new Producto(codigo, descripcion, unidadMedida));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }
}
