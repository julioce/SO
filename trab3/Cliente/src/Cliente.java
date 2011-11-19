import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
	
	static int portNumber;
	static String host;
	static String command;
	static Socket clientSocket = null;
	static PrintStream os = null;
	static DataInputStream is = null;
	static BufferedReader inputLine = null;
	
	public Cliente(){}

	public static void setHost(String host) {
		Cliente.host = host;
	}

	public static void setPortNumber(int portNumber) {
		Cliente.portNumber = portNumber;
	}
	
	public static void startCliente(){
		// Tenta abrir o socket com o host e o socket
		// Tenta abrir streams de input e output
		try {
			
			clientSocket = new Socket(host, portNumber);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			
		} catch (UnknownHostException e) {
			System.err.println("Erro: Host nao reconhecido: " + host + e);
		} catch (Exception e) {
			System.err.println("Erro: Nao foi possivel abrir conexoes ao host: " + host + "\n" + e);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		try {
			// Envia o comando
			os.println(Cliente.command);
			
			// Imprime o que é recebido
			View.outputTextArea.setText(is.readLine());
			
		} catch (Exception e) {
			System.err.println("Erro: Nao foi possivel criar a thread de escuta do servidor " + e);
		}
	}

	public static void execute(String command) {
		Cliente.command = command;
		new Thread(new Cliente()).run();
	}

}