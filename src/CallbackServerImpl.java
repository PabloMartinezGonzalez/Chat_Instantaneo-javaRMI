
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the remote interface CallbackServerInterface.
 *
 * @author M. L. Liu
 */
public class CallbackServerImpl extends UnicastRemoteObject
        implements CallbackServerInterface {

    private HashMap<String, CallbackClientInterface> clientesConectados;
    //gui.FachadaGui fgui;
    FachadaBaseDatos fbd;

    public CallbackServerImpl() throws RemoteException {
        super();
        this.clientesConectados = new HashMap<>();
        this.fbd = new FachadaBaseDatos();
    }

    public String sayHello()
            throws java.rmi.RemoteException {
        return ("hello");
    }

    @Override
    public synchronized Boolean iniciarSesion(CallbackClientInterface callbackClientObject, String usuario, String passwrd)
            throws java.rmi.RemoteException, SQLException {

        if (!(clientesConectados.containsKey(usuario))) {
            clientesConectados.put(usuario, callbackClientObject);
            System.out.println("Registered new client ");
            doCallbacks();
            return true;
        } // end if
        return false;
    }

    @Override
    public synchronized Boolean registrarUsuario(CallbackClientInterface callbackClientObject, String idUsuario, String passwrd)
            throws java.rmi.RemoteException, SQLException {

        ArrayList<String> usuarios = fbd.obtenerUsuarios();
        // store the callback object into the vector
        if (!(usuarios.contains(idUsuario))) {
            clientesConectados.put(idUsuario, callbackClientObject);
            fbd.nuevoUsuario(idUsuario, passwrd);
            System.out.println("Registered new client ");
            doCallbacks();
            return true;
        } // end if
        return false;
    }

    private synchronized void quitarConectados(String usuario) throws SQLException, RemoteException{
        ArrayList<String> listaAmigos = obtenerAmigos(usuario, "");
        for (String amigo : listaAmigos) {
            
            if (clientesConectados.containsKey(amigo)) {
                clientesConectados.get(amigo).amigoNoConectado(usuario);
            }
        }
    }
    @Override
    public synchronized void unregisterForCallback(String idUsuario) throws java.rmi.RemoteException, SQLException {

        if (clientesConectados.containsKey(idUsuario)) {
            clientesConectados.remove(idUsuario);
            System.out.println("Unregistered client ");
            quitarConectados(idUsuario);
        } else {
            System.out.println(
                    "unregister: clientwasn't registered.");
        }
    }
    

    public synchronized void actualizarConectados(String usuario) throws SQLException, java.rmi.RemoteException{

        ArrayList<String> listaAmigos = obtenerAmigos(usuario, "");
        for (String amigo : listaAmigos) {
            if (clientesConectados.containsKey(amigo)) {
                clientesConectados.get(usuario).nuevoAmigoConectado(clientesConectados.get(amigo), amigo);
            }
        }
    }
    private synchronized void doCallbacks() throws java.rmi.RemoteException, SQLException {
    // make callback to each registered client

        for (String cliente : clientesConectados.keySet()) {
            actualizarConectados(cliente);
        }
        System.out.println("********************************\nServer completed callbacks ---");

    } // doCallbacks

    @Override
    public synchronized String comprobarAutentificacion(String idUsuario, String clave) throws java.rmi.RemoteException {
        return fbd.validarUsuario(idUsuario, clave);
    }

    @Override
    public synchronized void actualizarClave(String usuario, String clave) throws SQLException, java.rmi.RemoteException {
            fbd.actualizarClave(usuario, clave);
    }

    @Override
    public synchronized ArrayList<String> obtenerAmigos(String idUsuario, String idAmigo) throws SQLException, java.rmi.RemoteException {
        return fbd.obtenerAmigos(idUsuario, idAmigo);
    } 
   @Override
    public synchronized HashMap<String, CallbackClientInterface> amigosConectados(String idUsuario) throws SQLException, java.rmi.RemoteException {
        HashMap<String,CallbackClientInterface> amigosConectados = new HashMap<>();
        ArrayList<String> listaAmigos = obtenerAmigos(idUsuario, "");
        for (String amigo : listaAmigos) {
            if (clientesConectados.containsKey(amigo)) {
                amigosConectados.put(amigo, clientesConectados.get(amigo));
            }
        }
        return amigosConectados;
    }
    
    @Override
    public synchronized void aceptarSolicitud1(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException {
        fbd.aceptarSolicitud1(idUsuario1, idUsuario2);
    }
    
    @Override
    public synchronized void aceptarSolicitud2(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException {
        fbd.aceptarSolicitud2(idUsuario1, idUsuario2);
      
    }
    
    
    @Override
    public synchronized void rechazarSolicitud(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException {
        fbd.rechazarSolicitud(idUsuario1, idUsuario2);
    }
    
    
    @Override
    public synchronized void hacerSolicitud(String idUsuario1, String idUsuario2)throws SQLException, java.rmi.RemoteException {
        fbd.hacerSolicitud(idUsuario1, idUsuario2);
    }
    
    @Override
    public synchronized boolean existeUsuario(String idUsuario1)throws SQLException, java.rmi.RemoteException {
        return fbd.existeUsuario(idUsuario1);
    }
    
    @Override
    public synchronized ArrayList<String> buscarUsuariosSolictudes(String idUsuario)throws SQLException, java.rmi.RemoteException {
        return fbd.buscarUsuariosSolictudes(idUsuario);
    }
    
    
    @Override
    public synchronized ArrayList<String> misSolicitudes(String idUsuario) throws SQLException, java.rmi.RemoteException{
        return fbd.misSolicitudes(idUsuario);
    }
    
    @Override
    public synchronized ArrayList<String> solicitudesEntrantes(String idUsuario) throws SQLException, java.rmi.RemoteException {
        return fbd.solicitudesEntrantes(idUsuario);
    }
    
    @Override
    public synchronized void elminarSolicitud(String idUsuario1, String idUsuario2) throws SQLException, java.rmi.RemoteException{
        fbd.elminarSolicitud(idUsuario1, idUsuario2);
    }
   

}// end CallbackServerImpl class   
