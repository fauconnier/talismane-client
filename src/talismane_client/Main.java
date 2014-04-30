package talismane_client;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
	
		TalismaneClient talismane_client = new TalismaneClient("localhost", 7272);
		
		/* Server isAlive? */
		try {
			System.out.println(talismane_client.serverIsAlive());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* analyse a String */
		try {
			talismane_client.analyse("Phrase de la mort. Deuxième phrase. Ma phrase à moi.");
			
//			talismane_client.analyse("Deuxième phrase");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
