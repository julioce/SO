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

	// Inicializa o Cliente
	public void startCliente(){
		// Tenta abrir o socket com o host e o socket
		// Tenta abrir streams de input e output
		try {
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			
			// Se não houve erros inicializa o cliente de fato
			if (!clientSocket.equals(null) && !os.equals(null) && !is.equals(null)) {
				try {
					// Cria a thread do Cliente
					new Thread(new Cliente()).start();
					
					// Enquanto não encerra o servidor a conexão mostra o input
					while (!closed) {
						os.println(inputLine.readLine());	
					}
					
					// Limpa o output, input e fecha o socket
					os.close();
					is.close();
					clientSocket.close();
					
					// Termina thread fechando o cliente
					System.exit(0);
				} catch (Exception e) {
					System.err.println("Erro: Nao foi possivel ler o comando passado " + e);
				}
			}
			
		} catch (UnknownHostException e) {
			System.err.println("Erro: Host nao reconhecido: " + host + e);
		} catch (Exception e) {
			System.err.println("Erro: Nao foi possível abrir conexoes ao host: " + host + "\n" + e);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		String responseLine;
	
		// Recebe informaçõees do socket até receber um término do servidor
		try {
			while ( (responseLine = is.readLine()) != null ) {
				System.out.println(responseLine);
				
				// Recebeu um término do servidor
				if (responseLine.indexOf("*** Bye") != -1){
					break;
				}
			}
			
			// Fecha a conexão
			closed = true;
		} catch (Exception e) {
			System.err.println("Erro: Nao foi possivel criar a thread de escuta do servidor " + e);
		}
	}

}