package talismane_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class TalismaneClient {
	
	String host;
	int port;
	
	public TalismaneClient(){
		this.host="localhost";
		this.port=7272;
	}
	
	public TalismaneClient(String host, int port){
		this.host=host;
		this.port=port;
	}
	
	public boolean serverIsAlive() throws UnknownHostException, IOException{

		Socket socket=null;
		boolean isAlive=false;
		try{
			socket = new Socket(host, port);
			isAlive=true;
		}
		finally{
			if(socket!=null){
				socket.close();
			}
		}
		return isAlive;
	}
	
	public void analyse(String text) throws UnknownHostException, IOException{
		Socket socket = new Socket(host, port);
		OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"));
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
		
		String output;
		text = "\f" + text + "\f\f\f";
		
		out.write(text);
		out.flush();
		
		while ((output = in.readLine()) != null) {
			System.out.println(output);
		}

		socket.close();
	}
	
//	public void analyse(File file){
//		
//		
//		
//	}
	
	

}
