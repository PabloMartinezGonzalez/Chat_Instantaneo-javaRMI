import java.rmi.*;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a remote interface for illustrating RMI 
 * client callback.
 * @author M. L. Liu
 */

public interface CallbackClientInterface 
  extends java.rmi.Remote{
  // This remote method is invoked by a callback
  // server to make a callback to an client which
  // implements this interface.
  // @param message - a string containing information for the
  //                  client to process upon being called back.

    public String nuevoAmigoConectado(CallbackClientInterface cliente, String idusuario)throws RemoteException;
    
    public void amigoNoConectado(String amigo)throws RemoteException;
    public void setVp(VPrincipal vp) throws RemoteException;
    public VPrincipal getVp() throws RemoteException;
    
    public String getId()throws RemoteException, java.rmi.server.ExportException;

    public void setId(String id)throws java.rmi.RemoteException, java.rmi.server.ExportException;
 

    public Boolean recibirMensaje(String usuario, String mensaje)throws java.rmi.RemoteException, java.rmi.server.ExportException;
    
    public void actualizarSolicitudes()throws java.rmi.RemoteException, java.rmi.server.ExportException, SQLException;
    
    public void actualizarAmigos()throws java.rmi.RemoteException, java.rmi.server.ExportException, SQLException;
//    public ArrayList<String> getAmigos()throws java.rmi.RemoteException, java.rmi.server.ExportException;
//    
//    public void setAmigos(ArrayList<String> amigos)throws java.rmi.RemoteException, java.rmi.server.ExportException;

} // end interface
