import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class Atendente extends Thread {

	static int numbersOfClients = 5;
	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	Atendente t[];

	public Atendente(Socket clientSocket, Atendente[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}
	
	// Imprime a mensagem para o Atendente
	private void printMessageClient(String line){
		for (int i = 0; i < numbersOfClients; i++) {
			if (t[i] != null) {
				t[i].os.println(line);
			}
		}
	}
	
	// Imprime a mensagem para o Atendente
	private void printMessageOutServer(){
		for (int i = 0; i < numbersOfClients; i++) {
			if(t[i] == this){
				System.out.println("Cliente de numero " + (i+1) + " deixou o servidor");	
			}
		}
	}
	
	
	private void cleanServer() {
		for (int i = 0; i < numbersOfClients; i++) {
			if (t[i] == this) {
				t[i] = null;
			}
		}
	}
	

	private String runCommand(String command){
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String commandOutput = null;
		
		// Tenta executar o commando
		try {
			process = runtime.exec(command);
			System.out.println("Foi executado o comando <" + command + ">");
		} catch (IOException e) {
			System.err.println("Erro ao tentar executar o comando <" + command + ">\nVerifique se o comando esta correto.");
		}
		
		// Tenta ler a saida do comando
		try {
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			commandOutput = br.readLine();
		} catch (IOException e) {
			System.err.println("Erro ao tentar executar o comando <" + command + ">\nVerifique se o comando esta coerreto.");
		}
		return commandOutput;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String line;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());

			// Loop de execução
			while (true) {
				// Faz a leitura do console
				line = is.readLine();
				
				// Sai do loop se digitado palavra chave
				if (line == null) {
					break;
				}else{
					line = runCommand(line);
				}

				// Imprime a mensagem para o Atendente
				printMessageClient(line);
			}

			// Imprime mensagem para o Servidor
			printMessageOutServer();

			// Limpeza do slot do servidor liberando para outro cliente se conectar
			cleanServer();

			// Fecha o input, output e o socket
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Erro ao iniciar a Thread do servidor para o Atendente " + e);
		}
	}
	
}