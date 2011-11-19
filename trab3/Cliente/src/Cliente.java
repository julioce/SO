import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
	
	static String host;
	static int portNumber;
	static Socket clientSocket = null;
	static PrintStream outputStream = null;
	static DataInputStream inputStream = null;
	static BufferedReader inputLine = null;
	static String command;
	
	public Cliente(){}

	public static void setHost(String host) {
		Cliente.host = host;
	}

	public static void setPortNumber(int portNumber) {
		Cliente.portNumber = portNumber;
	}
	
	public static void startCliente(){
		// Tenta abrir o socket com o host e port
		// Tenta abrir streams de input e output
		try {
			
			clientSocket = new Socket(host, portNumber);
			outputStream = new PrintStream(clientSocket.getOutputStream());
			inputStream = new DataInputStream(clientSocket.getInputStream());
			
		} catch (UnknownHostException e) {
			System.err.println("Erro: Host nao reconhecido: " + host);
		} catch (Exception e) {
			System.err.println("Erro: Nao foi possivel abrir conexoes ao host " + host + " na porta " + portNumber);
		}
	}
	
	// Executa o comando solicitado Localmente
	public static void runLocalCommand(String command){
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String readLine = "";
		
		// Tenta executar o commando
		try {
			process = runtime.exec(command);
		} catch (Exception e) {}
		
		// Tenta ler a saida do comando
		try {
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			View.ClientFileList.clear();
			while( (readLine = br.readLine()) != null) {  
				View.ClientFileList.addElement(readLine);
			}
			
		} catch (Exception e) {
			System.err.println("Erro ao obter saida de comandos.");
		}
	}
	
	public void run() {
		String print = "";
		String[] output = null;
		
		// Faz a leitura
		try {
			// Envia o comando que foi clicado
			outputStream.println(Cliente.command);
						
			// Recebe o que foi processado
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			print = br.readLine();
			output = print.split("@end#");
			
			View.ServerFileList.clear();
			for(int i=0; i<output.length; i++){
				// Imprime no campo
				View.ServerFileList.addElement(output[i]);
			}
			
		} catch (IOException e) {
			System.err.println("Erro: Nao foi possivel ter conexao com o servidor");
		} catch (NullPointerException e){}
		
	}

	public static void execute(String command) {
		Cliente.command = command;
		new Thread(new Cliente()).run();
	}

}