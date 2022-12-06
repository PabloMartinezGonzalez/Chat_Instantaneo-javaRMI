import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the remote interface 
 * CallbackClientInterface.
 * @author M. L. Liu
 */

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {
    
    private String id;
    private ArrayList<String> amigos;
    private VPrincipal vp;
    
    
   public CallbackClientImpl() throws RemoteException {
      super( );
      this.amigos=new ArrayList<>();
      id = null;
      this.vp = new VPrincipal();
   }

    @Override
    public VPrincipal getVp() throws RemoteException{
        return vp;
    }

    @Override
    public void setVp(VPrincipal vp) throws RemoteException{
        this.vp = vp;
    }

    @Override
   public String nuevoAmigoConectado(CallbackClientInterface cliente, String idusuario)throws RemoteException{
      vp.nuevoAmigoConectado(cliente, idusuario);
      String returnMessage = "New conected friend registered";
      return returnMessage;
   } 
   
    @Override
   public void amigoNoConectado(String amigo)throws RemoteException{
       vp.quitarConectado(amigo);
   }

    @Override
    public String getId()throws RemoteException, ExportException {
        return id;
    }

    @Override
    public void setId(String id)throws RemoteException, ExportException {
        this.id = id;
    }
   
   
    
    @Override
    public Boolean recibirMensaje(String usuario, String mensaje)throws java.rmi.RemoteException, java.rmi.server.ExportException{
        
        return vp.recibirMensaje(usuario, mensaje);
        
    }
    
    @Override
    public void actualizarSolicitudes()throws java.rmi.RemoteException, java.rmi.server.ExportException, SQLException{
        vp.getVs().actualizaTablas();
    }
    
    @Override
    public void actualizarAmigos()throws java.rmi.RemoteException, java.rmi.server.ExportException, SQLException{
        vp.actualizarAmigos();
    }


//    @Override
//    public ArrayList<String> getAmigos() throws RemoteException, ExportException {
//        return amigos;
//    }
//
//    @Override
//    public void setAmigos(ArrayList<String> amigos)throws RemoteException, ExportException {
//        this.amigos = amigos;
//    }

    
}// end CallbackClientImpl class   
