import java.io.BufferedReader;
import java.io.DataInputStream;
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
	
	// Imprime a mensagem para o Servidor
	private void printMessageOutServer(){
		for (int i = 0; i < numbersOfClients; i++) {
			if(t[i] == this){
				System.out.println("Cliente de numero " + (i+1) + " deixou o servidor");
			}
		}
	}
	
	// Mantem o servidor aberto para outros clientes
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
		} catch (Exception e) {
			System.err.println("Erro ao tentar executar o comando <" + command + ">\nVerifique se o comando esta correto.");
		}
		
		// Tenta ler a saida do comando
		try {
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			commandOutput = br.readLine();
		} catch (Exception e) {
			System.err.println("Erro ao obter os comandos.");
		}
		
		return commandOutput;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String line = null;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());

			// Loop de leitura
			while (true) {
				// Faz a leitura do que foi passado
				line = is.readLine();
				
				// Sai do loop se digitado palavra chave
				if (line.startsWith("disconnect")) {
					// Imprime mensagem de saída para o Servidor
					printMessageOutServer();

					// Fecha o input, output e o socket
					is.close();
					os.close();
					clientSocket.close();
					
					// Limpeza do slot do servidor liberando para outro cliente se conectar
					cleanServer();
					
					break;
				}else{
					// Executa o comando normalmente
					line = runCommand(line);	
				}
				
				// Imprime a mensagem para o Cliente
				printMessageClient(line);
			}
			
		} catch (Exception e) {
			System.err.println("Erro ao iniciar a Thread do servidor para o Atendente");
		}
	}
	
}