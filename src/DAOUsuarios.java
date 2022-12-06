/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolás Fernández
 */
public class DAOUsuarios extends AbstractDAO {

    public DAOUsuarios(Connection conexion) {
        super.setConexion(conexion);
    }

    //Ver si el usuario puede iniciar sesion
    public String validarUsuario(String idUsuario, String clave) {
        String resultado = null;
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;

        con = this.getConexion();
        String encriptada = encript(clave);

        try {
            con.setAutoCommit(true);
            stmUsuario = con.prepareStatement("select id_usuario "
                    + "from cliente "
                    + "where id_usuario = ? and passwrd = ?");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, encriptada);

            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                resultado = rsUsuario.getString("id_usuario");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    //REgistrar a un nuevo usuario
    public void nuevoUsuario(String idUsuario, String clave) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();
        String encriptada = encript(clave);
        try {
            con.setAutoCommit(false); //Desactivamos el autocommit

            stmUsuario = con.prepareStatement("INSERT INTO cliente "
                    + "VALUES (?, ?)");
            stmUsuario.setString(1, idUsuario);
            stmUsuario.setString(2, encriptada);

            stmUsuario.executeUpdate();

            con.commit();     //reflejar las operaciones en la base de datos

        } catch (SQLException exc) {  // Si se produce una excepcion deshacemos las operaciones
            System.out.println(exc.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(exc.getMessage());

            if (con != null) {
                try {
                    con.rollback();   // Deshacer operaciones
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }

        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException exc) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    //Ver si el id ya pertenece a otro usuario registrado
    public boolean existeUsuario(String idUsuario) {
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        boolean disponible = true;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            stmUsuario = con.prepareStatement("select * "
                    + "from cliente "
                    + "where id_usuario = ?");
            stmUsuario.setString(1, idUsuario);

            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()) {
                disponible = false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return disponible;
    }

    public ArrayList<String> obtenerUsuarios(){
        java.util.ArrayList<String> resultado = new ArrayList<>();
        String usuario = null;
        Connection con;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmUsuarios = con.prepareStatement("select id_usuario "
                    + "from cliente ");

           
            rsUsuarios = stmUsuarios.executeQuery();

            while (rsUsuarios.next()) {
                usuario = rsUsuarios.getString("id_usuario");

                resultado.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }
    //Obtiene la lista de amigos de una persona
    public ArrayList<String> obtenerAmigos(String idUsuario, String idAmigo) throws SQLException {
        java.util.ArrayList<String> resultado = new ArrayList<>();
        String amigo = null;
        Connection con;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmUsuarios = con.prepareStatement("select id_usuario2 "
                    + "from amigos "
                    + "where id_usuario1 = ? and id_usuario2 like ? ");

            stmUsuarios.setString(1,idUsuario);
            stmUsuarios.setString(2,"%"+idAmigo+"%");
            

            rsUsuarios = stmUsuarios.executeQuery();

            while (rsUsuarios.next()) {
                amigo = rsUsuarios.getString("id_usuario2");

                resultado.add(amigo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }
    
    //Obtiene las solicitudes de amistas que ha enviado una persona y que no han sido contestadas
     public ArrayList<String> misSolicitudes(String idUsuario) throws SQLException {
        java.util.ArrayList<String> resultado = new ArrayList<>();
        String amigo = null;
        Connection con;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmUsuarios = con.prepareStatement("select id_solicitado "
                    + "from solicitudes "
                    + "where id_solicitante = ?");

            stmUsuarios.setString(1,idUsuario );
            

            rsUsuarios = stmUsuarios.executeQuery();

            while (rsUsuarios.next()) {
                amigo = rsUsuarios.getString("id_solicitado");

                resultado.add(amigo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }
     
     //obtiene las solicitudes pendientes de aceptar o rechazar para ese usuario
    public ArrayList<String> solicitudesEntrantes(String idUsuario) throws SQLException {
        java.util.ArrayList<String> resultado = new ArrayList<>();
        String amigo = null;
        Connection con;
        PreparedStatement stmUsuarios = null;
        ResultSet rsUsuarios;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmUsuarios = con.prepareStatement("select id_solicitante "
                    + "from solicitudes "
                    + "where id_solicitado = ?");

            stmUsuarios.setString(1,idUsuario );
            

            rsUsuarios = stmUsuarios.executeQuery();

            while (rsUsuarios.next()) {
                amigo = rsUsuarios.getString("id_solicitante");

                resultado.add(amigo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuarios.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }
    
    public void hacerSolicitud(String idUsuario1, String idUsuario2) throws SQLException {
       
        Connection con;
        PreparedStatement stmSolicitud = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmSolicitud = con.prepareStatement("INSERT INTO solicitudes "
                    + "VALUES (?, ?)");
            stmSolicitud.setString(1, idUsuario1);
            stmSolicitud.setString(2, idUsuario2);
            

            stmSolicitud.executeQuery();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSolicitud.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    public void elminarSolicitud(String idUsuario1, String idUsuario2) throws SQLException{
        Connection con;
        PreparedStatement stmSolicitud = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmSolicitud = con.prepareStatement("DELETE FROM solicitudes "
                    + "where id_solicitante = ?"
                    + "and id_solicitado = ?");
            stmSolicitud.setString(1, idUsuario1);
            stmSolicitud.setString(2, idUsuario2);
            

            stmSolicitud.executeQuery();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSolicitud.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
     
    //Acepta una solicitud de amistad. Elimina todas las solicitudes bidireccionales entre esos dos usuarios
    public void aceptarSolicitud1(String idUsuario1, String idUsuario2) throws SQLException {
       
        Connection con;
        PreparedStatement stmSolicitud = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmSolicitud = con.prepareStatement("delete from solicitudes "
                    + "where id_solicitante = ? and id_solicitado = ? ");
            stmSolicitud.setString(1, idUsuario2);
            stmSolicitud.setString(2, idUsuario1);
            stmSolicitud.executeQuery();     
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSolicitud.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    
    
    
    
    public void aceptarSolicitud2(String idUsuario1, String idUsuario2) throws SQLException {
       
        Connection con;
        PreparedStatement stmSolicitud = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmSolicitud = con.prepareStatement("insert into amigos "
                    + "values (?, ?)");
            stmSolicitud.setString(1, idUsuario1);
            stmSolicitud.setString(2, idUsuario2);
            stmSolicitud.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSolicitud.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
     
    //Rechaza una solicitud de amistad
    public void rechazarSolicitud(String idUsuario1, String idUsuario2) throws SQLException {
       
        Connection con;
        PreparedStatement stmSolicitud = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            
            stmSolicitud = con.prepareStatement("delete from solicitudes "
                    + "where id_solicitante = ? and id_solicitado = ?");
            stmSolicitud.setString(1, idUsuario1);
            stmSolicitud.setString(2, idUsuario2);
            stmSolicitud.executeQuery();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSolicitud.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    //Actualiza la contraseña de un usuario
    public void actualizarClave(String id_usuario, String clave) {
        Connection con;
        PreparedStatement stmUsuario = null;

        con = super.getConexion();
        String encriptada = encript(clave);

        try {
            con.setAutoCommit(true);

            stmUsuario = con.prepareStatement("update cliente "
                    + "set passwrd = ? "
                    + "where id_usuario = ?");
            stmUsuario.setString(1, encriptada);
            stmUsuario.setString(2, id_usuario);

            stmUsuario.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    
    public ArrayList<String> buscarUsuariosSolictudes(String idUsuario) {
        Connection con;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;
        String usuario=null;
        ArrayList<String> usuarios= new ArrayList<>();

        con = this.getConexion();

        try {
            con.setAutoCommit(true);
            stmUsuario = con.prepareStatement("select id_usuario "
                    + "from cliente "
                    + "where id_usuario like ?");
            stmUsuario.setString(1, "%"+idUsuario+"%");

            rsUsuario = stmUsuario.executeQuery();

            while (rsUsuario.next()) {
                usuario = rsUsuario.getString("id_usuario");
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmUsuario.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return usuarios;
    }
    
    public String desencript(String encriptado) {
        int i, ascii;
        char[] codigo = new char[encriptado.length()];

        codigo[0] = encriptado.charAt(0);

        for (i = 1; i < encriptado.length(); i++) {
            ascii = ((encriptado.charAt(i) + 128 - codigo[i - 1])) % 128;
            codigo[i] = (char) ascii;
        }

        return String.valueOf(codigo);
    }

    public String encript(String codigo) {
        int i, ascii;
        char[] encriptado = new char[codigo.length()];

        encriptado[0] = codigo.charAt(0);

        for (i = 1; i < codigo.length(); i++) {
            ascii = ((int) codigo.charAt(i) + (int) codigo.charAt(i - 1)) % 128;
            encriptado[i] = (char) ascii;
        }

        return String.valueOf(encriptado);
    }

}
