import java.rmi.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a remote interface for illustrating RMI 
 * client callback.
 * @author M. L. Liu
 */

public interface CallbackServerInterface extends Remote {

  public String sayHello( )  throws java.rmi.RemoteException;

// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.

  public Boolean iniciarSesion(CallbackClientInterface callbackClientObject, String usuario, String passwrd)
    throws java.rmi.RemoteException, SQLException;
  
  public Boolean registrarUsuario(CallbackClientInterface callbackClientObject, String idUsuario, String passwrd)
    throws java.rmi.RemoteException, SQLException;

// This remote method allows an object client to 
// cancel its registration for callback

  public void unregisterForCallback(String idUsuario)
    throws java.rmi.RemoteException, SQLException;
   
  public String comprobarAutentificacion(String idUsuario, String clave) throws java.rmi.RemoteException;
 
  
  public void actualizarClave(String usuario, String clave) throws SQLException, java.rmi.RemoteException ;
  
  public ArrayList<String> obtenerAmigos(String idUsuario, String idAmigo) throws SQLException, java.rmi.RemoteException;
  
  public HashMap<String, CallbackClientInterface> amigosConectados(String idUsuario) throws SQLException, java.rmi.RemoteException;
  
  public void actualizarConectados(String usuario) throws SQLException, java.rmi.RemoteException;
          
  public void aceptarSolicitud1(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException;
  
  public void aceptarSolicitud2(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException;
  
  public void rechazarSolicitud(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException;
  
  public void hacerSolicitud(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException;
  
  public boolean existeUsuario(String idUsuario1)throws SQLException, java.rmi.RemoteException;
  
  public ArrayList<String> buscarUsuariosSolictudes(String idUsuario)throws SQLException, java.rmi.RemoteException;
  
  public ArrayList<String> misSolicitudes(String idUsuario) throws SQLException, java.rmi.RemoteException;
  
  public ArrayList<String> solicitudesEntrantes(String idUsuario) throws SQLException, java.rmi.RemoteException;
  
  public void elminarSolicitud(String idUsuario1, String idUsuario2) throws SQLException, java.rmi.RemoteException;
}
