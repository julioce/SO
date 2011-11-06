import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

class Cliente extends Thread{
	
	static int numbersOfClients = 5;
	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	Cliente t[];
	
	public Cliente(Socket clientSocket, Cliente[] t){
		this.clientSocket=clientSocket;
		this.t = t;
	}
	
	@SuppressWarnings("deprecation")
	public void run(){
		String line;
		String name;
		
		try{
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			os.println("Enter your name.");
			name = is.readLine();
			os.println("Hello "+name+" to our chat room.\nTo leave enter /quit in a new line");
			
			for(int i=0; i<=numbersOfClients; i++){
				if (t[i]!=null && t[i]!=this){
					t[i].os.println("*** A new user "+name+" entered the chat room !!! ***" );
				}
			}
			
			while (true) {
				line = is.readLine();
				if(line.startsWith("/quit")){
					break;
				}
				
				for(int i=0; i<=numbersOfClients; i++){
					if (t[i]!=null){
						t[i].os.println("<"+name+"> "+line); 
					}
				}
			}

			for(int i=0; i<=numbersOfClients; i++){
				if (t[i]!=null && t[i]!=this){
					t[i].os.println("*** The user "+name+" is leaving the chat room !!! ***" );
				}
			}
			
			os.println("*** Bye "+name+" ***");
			
			// Limpeza do slot do servidor liberando para outro cliente se conectar
			for(int i=0; i<=numbersOfClients; i++){
				if (t[i] == this){
					t[i] = null;  
				}
			}
			
			// Fecha o input, output e o socket
			is.close();
			os.close();
			clientSocket.close();
			
		} catch(IOException e){
			System.err.println("Erro ao iniciar a Thread do servidor para o Cliente "+e);
		}
    }
}