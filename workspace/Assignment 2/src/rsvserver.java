import java.rmi.Naming;
import java.rmi.registry.*;

public class rsvserver {

	 
	public rsvserver() {
		
		try {
			
			LocateRegistry.createRegistry(1099);
			rsvImpl c = new rsvImpl();
			Naming.rebind("rmi://localhost/rsvserver", c);
			
			} catch (Exception e) {
				System.err.println(e);
				}
	}
		
	public static void main (String[]args) {
		new rsvserver();
	}
}
