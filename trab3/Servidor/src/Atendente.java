import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class Atendente extends Thread {

	static int numbersOfClients = 5;
	DataInputStream inputStream = null;
	PrintStream outputStream = null;
	Socket clientSocket = null;
	Atendente t[];

	public Atendente(Socket clientSocket, Atendente[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}
	
	// Imprime a mensagem para o Servidor
	private void printMessageOutServer(){
		for (int i = 0; i < numbersOfClients; i++) {
			if(t[i] == this){
				System.out.println("Cliente conectado ao Atendente " + (i+1) + " desconectou-se do Servidor");
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
	

	// Executa o comando solicitado e retorna a sua saída
	private String runCommand(String command){
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String commandOutput = "";
		String readLine = "";
		
		// Tenta executar o commando
		try {
			process = runtime.exec(command);
			System.out.println("Foi executado o comando <" + command + ">");
		} catch (Exception e) {
			System.err.println("Erro ao tentar executar o comando <" + command + ">\nVerifique se o comando esta correto.");
		}
		
		// Tenta ler a saida do comando
		try {
			InputStream saidaComando = process.getInputStream();
			InputStreamReader leituraSaidaCommando = new InputStreamReader(saidaComando);
			BufferedReader leituraBuffer = new BufferedReader(leituraSaidaCommando);
			
			while( (readLine = leituraBuffer.readLine()) != null) {
				commandOutput += readLine + "@#";
			}
			
		} catch (Exception e) {
			System.err.println("Erro ao obter saida de comandos.");
		}
		
		return commandOutput;
		
	}
	
	//Realiza uma transferência de arquivos
	private byte[] transferFile(String filename, File file){
		
	    byte[] byteArray = null;
	    
		try {
			byteArray = new byte[(int) file.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(byteArray, 0, byteArray.length);

			System.out.println("Foi executado o download do arquivo " + filename);
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo " + filename + " não encontrado");
		} catch (IOException e) {
			System.err.println("Não foi possível ler o arquivo " + filename);
		}
		
		return byteArray;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String line = null;

		try {
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new PrintStream(clientSocket.getOutputStream());

			// Loop de leitura
			while (true) {
				// Faz a leitura do que foi passado
				line = inputStream.readLine();
				
				// Sai do loop se digitado palavra chave
				if (line.equalsIgnoreCase("disconnect")) {
					// Imprime mensagem de saída para o Servidor
					printMessageOutServer();

					// Fecha o input, output e o socket
					inputStream.close();
					outputStream.close();
					clientSocket.close();
					
					// Limpeza do slot do servidor liberando para outro cliente se conectar
					cleanServer();
					
					break;
				}else if(line.startsWith("receiveFileFromServer")){
					// Recebe o nome do arquivo
					String[] fileName = line.split("@#");
					
					// Cria o nome do arquivo
					File file = new File(fileName[1]);
					
					//Envia resposta ao Cliente específico
					for (int i = 0; i < numbersOfClients; i++) {
						if (t[i] != null) {
							t[i].outputStream.write(transferFile(fileName[1], file), 0, (int) file.length());
							t[i].outputStream.flush();
						}
					}
				}else{
					// Executa o comando normalmente
					line = runCommand(line);
					
					//Envia resposta ao Cliente específico
					for (int i = 0; i < numbersOfClients; i++) {
						if (t[i] != null) {
							t[i].outputStream.println(line);
							t[i].outputStream.flush();
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.err.println("Erro ao iniciar a Thread do servidor para o Atendente");
		}
	}
	
}