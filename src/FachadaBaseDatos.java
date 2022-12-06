/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author basesdatos
 */
public class FachadaBaseDatos {

    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;

    public FachadaBaseDatos() {
        
        Properties configuracion = new Properties();

        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);


            daoUsuarios = new DAOUsuarios(conexion);

        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
            //fa.muestraExcepcion(f.getMessage());
        } catch (IOException i) {
            System.out.println(i.getMessage());
           // fa.muestraExcepcion(i.getMessage());
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
           // fa.muestraExcepcion(e.getMessage());
        }

    }
    public String validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }
    public boolean existeUsuario(String idUsuario){
        return daoUsuarios.existeUsuario(idUsuario);
    }
    public void nuevoUsuario(String idUsuario, String clave){
        daoUsuarios.nuevoUsuario(idUsuario, clave);
    }
    
    public ArrayList<String> obtenerUsuarios() throws SQLException{
        return daoUsuarios.obtenerUsuarios();
    }
    public ArrayList<String> obtenerAmigos(String idUsuario, String idAmigo) throws SQLException{
        return daoUsuarios.obtenerAmigos(idUsuario, idAmigo);
    }
    
    public ArrayList<String> misSolicitudes(String idUsuario) throws SQLException{
        return daoUsuarios.misSolicitudes(idUsuario);
    }
    public ArrayList<String> solicitudesEntrantes(String idUsuario) throws SQLException{
        return daoUsuarios.solicitudesEntrantes(idUsuario);
    }
    public void hacerSolicitud(String idUsuario1, String idUsuario2) throws SQLException{
        daoUsuarios.hacerSolicitud(idUsuario1, idUsuario2);
    }
    public void aceptarSolicitud1(String idUsuario1, String idUsuario2) throws SQLException{
        daoUsuarios.aceptarSolicitud1(idUsuario1, idUsuario2);
    }
    public void aceptarSolicitud2(String idUsuario1, String idUsuario2) throws SQLException{
        daoUsuarios.aceptarSolicitud2(idUsuario1, idUsuario2);
    }
    public void rechazarSolicitud(String idUsuario1, String idUsuario2) throws SQLException{
        daoUsuarios.rechazarSolicitud(idUsuario1, idUsuario2);
    }
    public void actualizarClave(String idUsuario1, String clave) throws SQLException{
        daoUsuarios.actualizarClave(idUsuario1, clave);
    }
    
    public ArrayList<String> buscarUsuariosSolictudes(String idUsuario) throws SQLException{
        return daoUsuarios.buscarUsuariosSolictudes(idUsuario);
    }
    
    public void elminarSolicitud(String idUsuario1, String idUsuario2) throws SQLException{
        daoUsuarios.elminarSolicitud(idUsuario1, idUsuario2);
    }
}
