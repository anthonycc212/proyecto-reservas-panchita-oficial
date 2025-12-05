/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conex;

/**
 *
 * @author antho
 */
public class LoginFactory {
       public static login crearLogin(
            int id,
            String correo,
            String contrasena,
            String nombreCompleto,
            String rol,
            String telefono
    ) {
        login usuario = new login();
        usuario.setId(id);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setRol(rol);
        usuario.setTelefono(telefono);
        return usuario;
    }
}
