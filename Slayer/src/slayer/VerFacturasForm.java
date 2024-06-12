package slayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerFacturasForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public VerFacturasForm() {
        initComponents();
        cargarFacturas();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("MerlacStudios - Lista de Facturas");

        model = new DefaultTableModel(new Object[]{"Número", "Cliente", "Fecha", "Subtotal", "Descuento", "IVA", "Total", "Forma de Pago", "Status"}, 0);
        table = new JTable(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarFacturas() {
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
            String sql = "SELECT f.FACNUMERO, c.CLINOMBRE, f.FACFECHA, f.FACSUBTOTAL, f.FACDESCUENTO, f.FACIVA, (f.FACSUBTOTAL - f.FACDESCUENTO + f.FACIVA) AS Total, f.FACFORMAPAGO, f.FACSTATUS FROM FACTURAS f JOIN CLIENTES c ON f.CLICODIGO = c.CLICODIGO";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("FACNUMERO"),
                        rs.getString("CLINOMBRE"),
                        rs.getDate("FACFECHA"),
                        rs.getDouble("FACSUBTOTAL"),
                        rs.getDouble("FACDESCUENTO"),
                        rs.getDouble("FACIVA"),
                        rs.getDouble("Total"),
                        rs.getString("FACFORMAPAGO"),
                        rs.getString("FACSTATUS")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las facturas.", "Error", JOptionPane.ERROR_MESSAGE);
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
