import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
	
	static Socket clientSocket = null;
	static PrintStream os = null;
	static DataInputStream is = null;
	static BufferedReader inputLine = null;
	static boolean closed = false;
	
	public static void main(String[] args) {
		// Porta padr�o
		int port_number = 2222;
		String host="localhost";
		
		if (args.length > 1){
			host = args[0];
			port_number=Integer.valueOf(args[1]).intValue();
		}
		
		// Inicializa o servidor
		// Tenta abrir o socket com o host e o socket
		// Tenta abrir streams de input e output
		try {
			clientSocket = new Socket(host, port_number);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Erro: Host n�o reconhecido:" + host);
		} catch (IOException e) {
			System.err.println("Erro: N�o foi poss�vel abrir conex�es:" + host);
		}
	

		// Se n�o houve erros inicializa o cliente de fato
		if (clientSocket != null && os != null && is != null) {
			try {
				// Cria a thread do Cliente
				new Thread(new Cliente()).start();
				
				// Enquanto n�o fecha a conex�o mostra o input
				while (!closed) {
					os.println(inputLine.readLine());	
				}
				
				// Limpa o output, input e fecha o socket
				os.close();
				is.close();
				clientSocket.close();
				
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		String responseLine;
	
		//Recebe informa��es do socket at� receber um t�rmino do servidor, depois entramos non break;
		try {
			while ((responseLine = is.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("*** Bye") != -1){
					break;
				}
			}
            
			//Fecha a conex�o
			closed = true;
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
	}
}