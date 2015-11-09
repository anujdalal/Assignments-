import java.rmi.Naming;

public class rsvclient {

	public static void main (String[]args){
		try {
			 
			 
			rsvInterface c = (rsvInterface) Naming.lookup("rmi://localhost/rsverver");
			
		} catch (Exception e){
			System.err.println(e);
			}
	}
}
