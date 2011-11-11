import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
	
	int portNumber;
	String host;
	static Socket clientSocket = null;
	static PrintStream os = null;
	static DataInputStream is = null;
	static BufferedReader inputLine = null;
	static boolean closed = false;
	
	public Cliente(){
		// Construtor
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void startCliente(){
		// Inicializa o servidor
		// Tenta abrir o socket com o host e o socket
		// Tenta abrir streams de input e output
		try {
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Erro: Host não reconhecido: " + host);
		} catch (IOException e) {
			System.err.println("Erro: Não foi possível abrir conexões ao host: " + host);
		}
	

		// Se não houve erros inicializa o cliente de fato
		if (clientSocket != null && os != null && is != null) {
			try {
				// Cria a thread do Cliente
				new Thread(new Cliente()).start();
				
				// Enquanto não fecha a conexão mostra o input
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
	
		//Recebe informaçõees do socket até receber um término do servidor, depois entramos no break;
		try {
			while ((responseLine = is.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("*** Bye") != -1){
					break;
				}
			}
            
			//Fecha a conexão
			closed = true;
		} catch (IOException e) {
			System.err.println("Erro: IOException " + e);
		}
	}

}