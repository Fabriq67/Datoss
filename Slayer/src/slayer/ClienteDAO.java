package slayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT CLICODIGO, CLINOMBRE FROM CLIENTES";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigo = rs.getString("CLICODIGO");
                String nombre = rs.getString("CLINOMBRE");
                clientes.add(new Cliente(codigo, nombre));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;
    }
}
