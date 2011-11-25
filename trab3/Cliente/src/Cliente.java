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

	public static void setCommand(String command) {
		Cliente.command = command;
	}

	public static boolean startCliente(){
		// Tenta abrir o socket com o host e port e abrir streams de input e output
		try {
			
			clientSocket = new Socket(host, portNumber);
			outputStream = new PrintStream(clientSocket.getOutputStream());
			inputStream = new DataInputStream(clientSocket.getInputStream());
			return(true);
			
		} catch (UnknownHostException e) {
			View.showMessage("Host não reconhecido: " + host);
			return(false);
		} catch (Exception e) {
			View.showMessage("Não foi possível abrir conexões ao host " + host + " na porta " + portNumber);
			return(false);
		}
		
	}
	
	// Executa o comando solicitado Localmente
	public static void runLocalCommand(String command){
		Process process = null;
		String print = null;
		
		// Tenta executar o comando
		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);
		} catch (Exception e) {
			View.showMessage("Erro ao executar o comando.");
		}
		
		// Tenta ler a saida do comando
		try {
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			// Determina o que fazer na View baseado no que foi enviado ao Servidor	
			if (command.equalsIgnoreCase("ls -p")) {
				View.ClientFileList.clear();
				while((print = br.readLine()) != null){
					View.ClientFileList.addElement(print);
				}
				
			}else if(command.startsWith("ls -ltr ")){ 
				// Abre uma janela modal
				while((print = br.readLine()) != null){
					View.showMessage(print);
				}
			}
			
		} catch (Exception e) {
			View.showMessage("Erro ao obter saida de comandos.");
		}
	}
	
	public void run() {
		String output = "";
		String[] print = null;
		
		// Faz a leitura
		try {
			// Envia o comando que foi clicado
			outputStream.println(Cliente.command);
						
			// Recebe o que foi processado
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			output = br.readLine();
			print = output.split("@#");
			
			// Determina o que fazer na View baseado no que foi enviado ao Servidor
			if (Cliente.command.equalsIgnoreCase("ls -p")) {
				View.ServerFileList.clear();
				for(int i=0; i<print.length; i++){
					// Imprime no campo
					View.ServerFileList.addElement(print[i]);
				}
				
			}else if(Cliente.command.startsWith("ls -ltr ")){ 
				// Abre uma janela modal
				for(int i=0; i<print.length; i++){
					View.showMessage(print[i]);
				}
				
			}
			
		} catch (IOException e) {
			View.showMessage("Não foi possível ter conexão com o servidor");
		} catch (NullPointerException e){}
		
	}

	public static void execute(String command) {
		// Configura o comando e inicia uma nova Thread
		setCommand(command);
		new Thread(new Cliente()).run();
	}

}