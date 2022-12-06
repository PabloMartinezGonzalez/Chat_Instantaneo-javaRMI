import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

/**
 * This class represents the object server for a distributed
 * object of class Callback, which implements the remote 
 * interface CallbackInterface.
 * @author M. L. Liu
 */

public class CallbackServer  {
  public static void main(String args[]) {
    InputStreamReader is = 
      new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(is);
    String portNum, registryURL;
    try{     
      System.out.println(
        "Enter the RMIregistry port number:");
      portNum = (br.readLine()).trim();
      int RMIPortNum = Integer.parseInt(portNum);
      startRegistry(RMIPortNum);
      CallbackServerImpl exportedObj = 
        new CallbackServerImpl();
      registryURL = 
        "rmi://localhost:" + portNum + "/callback";
      Naming.rebind(registryURL, exportedObj);
      System.out.println("Callback Server ready.");
    }// end try
    catch (Exception re) {
      System.out.println(
        "Exception in HelloServer.main: " + re);
    } // end catch
  } // end main

  //This method starts a RMI registry on the local host, if
  //it does not already exists at the specified port number.
  private static void startRegistry(int RMIPortNum)
    throws RemoteException{
    try {
      Registry registry = 
        LocateRegistry.getRegistry(RMIPortNum);
      registry.list( );  
        // This call will throw an exception
        // if the registry does not already exist
    }
    catch (RemoteException e) { 
      // No valid registry at that port.
      Registry registry = 
        LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry

} // end class
