
public interface rsvInterface {
 
	public void list (int a, int b) throws java.rmi.RemoteException;
	
	public void passengerlist (String a, String b, int c) throws java.rmi.RemoteException;

	public void reserve (String c, String d, int e) throws java.rmi.RemoteException;
}
