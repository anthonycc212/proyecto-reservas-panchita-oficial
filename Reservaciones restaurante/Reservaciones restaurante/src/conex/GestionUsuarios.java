/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conex;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author antho
 */
public class GestionUsuarios extends JFrame {

   
    private JTable tabla;
    private DefaultTableModel modelo;
    private UsuarioDAO dao = new UsuarioDAO();

    public GestionUsuarios() {
        setTitle("Gestión de Usuarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Tabla
        String columnas[] = {"ID", "Nombre", "Correo", "Rol", "Teléfono", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 20, 650, 250);
        add(scroll);

        JButton btnActivar = new JButton("Activar");
        btnActivar.setBounds(150, 300, 120, 30);
        add(btnActivar);

        JButton btnDesactivar = new JButton("Desactivar");
        btnDesactivar.setBounds(350, 300, 120, 30);
        add(btnDesactivar);

        btnActivar.addActionListener(e -> {
            int id = obtenerIdSeleccionado();
            if (id == -1) return;
            if (dao.activarUsuario(id)) {
                JOptionPane.showMessageDialog(this, "Usuario activado correctamente");
                cargarUsuarios();
            }
        });

        btnDesactivar.addActionListener(e -> {
            int id = obtenerIdSeleccionado();
            if (id == -1) return;
            if (dao.desactivarUsuario(id)) {
                JOptionPane.showMessageDialog(this, "Usuario desactivado correctamente");
                cargarUsuarios();
            }
        });

        cargarUsuarios();
    }

    private int obtenerIdSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return -1;
        }
        return (int) tabla.getValueAt(fila, 0);
    }

    private void cargarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = dao.listar();
        if (lista == null) return;

        for (Usuario u : lista) {
            if (u == null) continue;

            int estadoVal = 0;

            try {
                // primero intenta usar el getter normalmente
                estadoVal = u.getEstado();
            } catch (NoSuchMethodError ex) {
                // si no existe el getter, intenta obtener por reflexión
                try {
                    var f = u.getClass().getDeclaredField("estado");
                    f.setAccessible(true);
                    Object val = f.get(u);
                    if (val instanceof Number) {
                        estadoVal = ((Number) val).intValue();
                    }
                } catch (Exception ignore) { }
            } catch (Throwable ex) {
                estadoVal = 0; // fallback seguro
            }

            String estadoStr = (estadoVal == 1) ? "Activo" : "Inactivo";

            modelo.addRow(new Object[]{
                u.getId(),
                u.getNombre_completo(),
                u.getCorreo(),
                u.getRol(),
                u.getTelefono(),
                estadoStr
            });
        }
    }

    public static void main(String[] args) {
        new GestionUsuarios().setVisible(true);
    }
}
