import java.io.*;
import java.net.*;

public class Servidor{
	
	static int numbersOfClients = 5;
	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;
	
	// Limite de 5 clientes
	static  Cliente t[] = new Cliente[numbersOfClients+1];
	
	public static void main(String args[]) {
		// Porta padr‹o
		int port_number = 2222;
		
		if (args.length > 1){
			port_number = Integer.valueOf(args[0]).intValue();
		}
		// Inicializa do servidor na porta - deve ser > 1023 se n‹o for root
		try {
			serverSocket = new ServerSocket(port_number);
			System.out.println("Servidor criado em " + port_number);
		} catch (IOException e){
			System.err.println("Erro ao criar o Servidor "+e);
		}
		
		// Cria o objeto do tipo ServerSocket para ouvir e aceitar conex›es e input e output
		// Input e output para este socket ser‹o criados na thread do cliente
		while(true){
			try {
				clientSocket = serverSocket.accept();
				
				// Cria as threads de acordo com o limite de clientes servidos
				for(int i=0; i<=numbersOfClients; i++){
					if(t[i]==null){
						(t[i] = new Cliente(clientSocket,t)).start();
						break;
					}
				}
			} catch (IOException e) {
				System.err.println("Erro ao criar Clientes para o Servidor "+e);
			}
		}
	}
}