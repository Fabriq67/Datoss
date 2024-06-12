package slayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.jdatepicker.impl.*;

public class FacturaForm extends JFrame {

    private JComboBox<String> cbCliente;
    private JComboBox<String> cbProducto;
    private JComboBox<String> cbFormaPago;
    private JComboBox<String> cbStatus;
    private JDatePickerImpl txtFecha;
    private JTextField txtDescuento;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtCantidad;
    private JTextField txtValorUnitario;
    private JTextField txtTotal;
    private DefaultTableModel model;
    private JTable table;

    public FacturaForm() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MerlacStudios - Sistema de Facturación Electrónica");

        // Inicializar componentes
        JLabel lblTitulo = new JLabel("MerlacStudios - Sistema de Facturación Electrónica", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 123, 255));

        cbCliente = new JComboBox<>(new String[]{"C000001 - JUAN PEREZ", "C000002 - MARIA LOPEZ"});
        cbProducto = new JComboBox<>(new String[]{"P0000001 - LAPTOP", "P0000002 - MOUSE"});
        cbFormaPago = new JComboBox<>(new String[]{"EFECT", "TARJC", "TRANS"});
        cbStatus = new JComboBox<>(new String[]{"ACT", "INA"});
        UtilDateModel modelFecha = new UtilDateModel();
        modelFecha.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl datePanel = new JDatePanelImpl(modelFecha, p);
        txtFecha = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        txtDescuento = new JTextField();
        txtSubtotal = new JTextField();
        txtIVA = new JTextField();
        txtCantidad = new JTextField();
        txtValorUnitario = new JTextField();
        txtValorUnitario.setEditable(false); // Hacer el campo no editable
        txtTotal = new JTextField();
        model = new DefaultTableModel(new Object[]{"Código", "Descripción", "Unidad", "Cantidad", "Valor Unitario", "Subtotal"}, 0);
        table = new JTable(model);

        // Colores
        Color backgroundColor = new Color(173, 216, 230); // Celeste claro
        Color buttonColor = new Color(0, 123, 255);
        Color textColor = new Color(255, 255, 255);

        getContentPane().setBackground(backgroundColor);

        // Layout y añadir componentes
        setLayout(null);

        add(lblTitulo).setBounds(10, 10, 620, 30);

        add(new JLabel("Cliente:")).setBounds(10, 50, 80, 25);
        add(cbCliente).setBounds(100, 50, 200, 25);

        add(new JLabel("Fecha:")).setBounds(320, 50, 80, 25);
        add(txtFecha).setBounds(410, 50, 200, 25);

        add(new JLabel("Descuento:")).setBounds(10, 80, 80, 25);
        add(txtDescuento).setBounds(100, 80, 200, 25);

        add(new JLabel("Subtotal:")).setBounds(320, 80, 80, 25);
        add(txtSubtotal).setBounds(410, 80, 200, 25);
        txtSubtotal.setEditable(false);

        add(new JLabel("IVA:")).setBounds(10, 110, 80, 25);
        add(txtIVA).setBounds(100, 110, 200, 25);
        txtIVA.setEditable(false);

        add(new JLabel("Forma de Pago:")).setBounds(320, 110, 100, 25);
        add(cbFormaPago).setBounds(410, 110, 200, 25);

        add(new JLabel("Status:")).setBounds(10, 140, 80, 25);
        add(cbStatus).setBounds(100, 140, 200, 25);

        add(new JScrollPane(table)).setBounds(10, 170, 600, 200);

        add(new JLabel("Producto:")).setBounds(10, 380, 80, 25);
        add(cbProducto).setBounds(100, 380, 200, 25);

        add(new JLabel("Cantidad:")).setBounds(320, 380, 80, 25);
        add(txtCantidad).setBounds(410, 380, 200, 25);

        add(new JLabel("Valor Unitario:")).setBounds(10, 410, 100, 25);
        add(txtValorUnitario).setBounds(100, 410, 200, 25);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(buttonColor);
        btnActualizar.setForeground(textColor);
        add(btnActualizar).setBounds(320, 410, 100, 25);
        btnActualizar.addActionListener(evt -> btnActualizarActionPerformed(evt));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(buttonColor);
        btnCerrar.setForeground(textColor);
        add(btnCerrar).setBounds(430, 410, 100, 25);
        btnCerrar.addActionListener(evt -> btnCerrarActionPerformed(evt));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(buttonColor);
        btnCancelar.setForeground(textColor);
        add(btnCancelar).setBounds(540, 410, 100, 25);
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));

        // Añadir botón Ver Facturas
        JButton btnVerFacturas = new JButton("Ver Facturas");
        btnVerFacturas.setBackground(buttonColor);
        btnVerFacturas.setForeground(textColor);
        add(btnVerFacturas).setBounds(10, 440, 150, 25);
        btnVerFacturas.addActionListener(evt -> btnVerFacturasActionPerformed(evt));

        add(new JLabel("Total:")).setBounds(320, 440, 80, 25);
        add(txtTotal).setBounds(410, 440, 200, 25);
        txtTotal.setEditable(false);

        setSize(650, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        String codigoProducto = ((String) cbProducto.getSelectedItem()).split(" - ")[0];
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double valorUnitario = obtenerValorProducto(codigoProducto);
        double subtotal = cantidad * valorUnitario;

        // Obtener descripción y unidad de medida del producto
        String descripcionProducto = obtenerDescripcionProducto(codigoProducto);
        String unidadProducto = obtenerUnidadProducto(codigoProducto);

        // Añadir a la tabla
        model.addRow(new Object[]{codigoProducto, descripcionProducto, unidadProducto, cantidad, valorUnitario, subtotal});
        calcularTotales();
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cerrar y guardar la factura?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Obtener datos del formulario
            String codigoCliente = (String) cbCliente.getSelectedItem();
            String codigoFactura = generarCodigoFactura();
            java.util.Date fecha = (java.util.Date) txtFecha.getModel().getValue();
            java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
            double subtotal = Double.parseDouble(txtSubtotal.getText());
            double descuento = Double.parseDouble(txtDescuento.getText());
            double iva = Double.parseDouble(txtIVA.getText());
            String formaPago = (String) cbFormaPago.getSelectedItem();
            String status = (String) cbStatus.getSelectedItem();
            double total = Double.parseDouble(txtTotal.getText());

            Connection connection = null;
            try {
                connection = Conexion.getConnection();

                // Insertar Factura
                String sqlFactura = "INSERT INTO FACTURAS (FACNUMERO, CLICODIGO, FACFECHA, FACSUBTOTAL, FACDESCUENTO, FACIVA, FACICE, FACFORMAPAGO, FACSTATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement psFactura = connection.prepareStatement(sqlFactura);
                psFactura.setString(1, codigoFactura);
                psFactura.setString(2, codigoCliente.split(" - ")[0]);
                psFactura.setDate(3, sqlFecha);
                psFactura.setDouble(4, subtotal);
                psFactura.setDouble(5, descuento);
                psFactura.setDouble(6, iva);
                psFactura.setDouble(7, 0); // ICE
                psFactura.setString(8, formaPago);
                psFactura.setString(9, status);
                psFactura.executeUpdate();

                // Insertar Productos en Factura
                String sqlProductosFactura = "INSERT INTO PXF (FACNUMERO, PROCODIGO, PXFCANTIDAD, PXFVALOR, PXFSUBTOTAL, PXFSTATUS) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psProductosFactura = connection.prepareStatement(sqlProductosFactura);
                for (int i = 0; i < model.getRowCount(); i++) {
                    psProductosFactura.setString(1, codigoFactura);
                    psProductosFactura.setString(2, model.getValueAt(i, 0).toString());
                    psProductosFactura.setDouble(3, Double.parseDouble(model.getValueAt(i, 3).toString()));
                    psProductosFactura.setDouble(4, Double.parseDouble(model.getValueAt(i, 4).toString()));
                    psProductosFactura.setDouble(5, Double.parseDouble(model.getValueAt(i, 5).toString()));
                    psProductosFactura.setString(6, "ACT");
                    psProductosFactura.addBatch();
                }
                psProductosFactura.executeBatch();

                JOptionPane.showMessageDialog(this, "Factura guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cancelar la operación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void btnVerFacturasActionPerformed(java.awt.event.ActionEvent evt) {
        SwingUtilities.invokeLater(() -> new VerFacturasForm());
    }

    private void calcularTotales() {
        double subtotal = 0.0;
        double descuento = Double.parseDouble(txtDescuento.getText());
        double iva;
        double total;
        for (int i = 0; i < model.getRowCount(); i++) {
            subtotal += Double.parseDouble(model.getValueAt(i, 5).toString());
        }
        iva = subtotal * 0.12; // Calcular IVA al 12%
        total = subtotal - descuento + iva;
        txtSubtotal.setText(String.valueOf(subtotal));
        txtIVA.setText(String.valueOf(iva));
        txtTotal.setText(String.valueOf(total));
    }

    private double obtenerValorProducto(String codigoProducto) {
        switch (codigoProducto) {
            case "P0000001":
                return 600.00;
            case "P0000002":
                return 15.00;
            default:
                return 0.0;
        }
    }

    private String obtenerDescripcionProducto(String codigoProducto) {
        switch (codigoProducto) {
            case "P0000001":
                return "LAPTOP";
            case "P0000002":
                return "MOUSE";
            default:
                return "";
        }
    }

    private String obtenerUnidadProducto(String codigoProducto) {
        switch (codigoProducto) {
            case "P0000001":
                return "UND";
            case "P0000002":
                return "UND";
            default:
                return "";
        }
    }

    private String generarCodigoFactura() {
        String codigoFactura = "";
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
            String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(FACNUMERO, 2) AS INTEGER)), 0) + 1 AS new_id FROM FACTURAS";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("new_id");
                codigoFactura = "F" + String.format("%08d", newId);
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
        return codigoFactura;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FacturaForm());
    }
}
