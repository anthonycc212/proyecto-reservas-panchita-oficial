/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conex;
 import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;


public class UsuarioDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    private int estado; // o Integer estado; si aceptas nulls

public int getEstado() {
    return estado;
}
public void setEstado(int estado) {
    this.estado = estado;
}
 // 🔵 ACTIVAR USUARIO
    public boolean activarUsuario(int id) {
        String sql = "UPDATE usuarios SET estado = 1 WHERE id = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar usuario: " + e.getMessage());
            return false;
        }
    }

    //  DESACTIVAR USUARIO
    public boolean desactivarUsuario(int id) {
        String sql = "UPDATE usuarios SET estado = 0 WHERE id = ?";

        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar usuario: " + e.getMessage());
            return false;
        }
    }
    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre_completo, correo, contrasena, rol, telefono) VALUES (?, ?, ?, ?, ?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre_completo());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getContrasena());
            ps.setString(4, u.getRol());
            ps.setString(5, u.getTelefono());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
public List<Usuario> listar() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios";

    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNombre_completo(rs.getString("nombre_completo"));
            u.setCorreo(rs.getString("correo"));
            u.setContrasena(rs.getString("contrasena"));
            u.setRol(rs.getString("rol"));
            u.setTelefono(rs.getString("telefono"));
            u.setEstado(rs.getInt("estado")); // 👈 si tu tabla lo tiene
            lista.add(u);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al listar usuarios: " + e.getMessage());
    }

    return lista;
}
   public Usuario buscarPorCorreo(String correo) {

    Usuario u = null;

    String sql = "SELECT * FROM usuarios WHERE correo=?";

    try {
        con = Conexion.getConexion();
        ps = con.prepareStatement(sql);
        ps.setString(1, correo);

        rs = ps.executeQuery();

        if(rs.next()) {

            u = new Usuario();

            u.setId(rs.getInt("id"));
            u.setNombre_completo(rs.getString("nombre_completo"));
            u.setCorreo(rs.getString("correo"));
            u.setContrasena(rs.getString("contrasena"));
            u.setRol(rs.getString("rol"));
            u.setTelefono(rs.getString("telefono"));
            u.setEstado(rs.getInt("estado"));

            // ✅ DEBUG para confirmar datos reales
            System.out.println(
                "CARGADO => " +
                u.getNombre_completo() + ", " +
                u.getCorreo() + ", " +
                u.getTelefono()
            );
        }

    } catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Error buscarPorCorreo: "+ e.getMessage());
    }

    return u;
}

    public boolean modificar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre_completo=?, contrasena=?, rol=?, telefono=? WHERE correo=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre_completo());
            ps.setString(2, u.getContrasena());
            ps.setString(3, u.getRol());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getCorreo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String correo) {
        String sql = "DELETE FROM usuarios WHERE correo=?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

   
}