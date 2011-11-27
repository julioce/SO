import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
	
	static String host;
	static int portNumber;
	static Socket clientSocket = null;
	static PrintStream outputStream = null;
	static DataInputStream inputStream = null;
	static String command;
	
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
	
	public static Process executeCommand(String command, String erro){
		// Tenta executar o comando
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);
		} catch (Exception e) {
			View.showMessage(erro);
		}
		
		return process;
	}
	
	public static BufferedReader readStream(){
		// Recebe o que foi processado
		InputStreamReader isr = new InputStreamReader(inputStream);
		return new BufferedReader(isr);
	}
	
	public static BufferedReader readProcess(Process process){
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		return new BufferedReader(isr);
	}
	
	// Executa o comando solicitado Localmente
	public static void runLocalCommand(String command){
		String print = null;
		
		// Tenta ler a saida do comando
		try {
			// Determina o que fazer na View baseado no que foi enviado ao Servidor	
			if (command.equalsIgnoreCase("ls -p")) {
				// Tenta executar o comando
				Process process = executeCommand(command, "Erro ao obter a listagem de arquivos locais.");
				
				// Lê saida do comando
				BufferedReader br = readProcess(process);
				
				// Gerencia a lista de arquivos
				View.ClientFileList.clear();
				while((print = br.readLine()) != null){
					if(!print.endsWith("/")){
						View.ClientFileList.addElement(print);
					}
				}
				
			}else if(command.startsWith("ls -ltr ")){ 
				// Tenta executar o comando
				Process process = executeCommand(command, "Erro ao obter informações do arquivo.");
				
				// Lê saida do comando
				BufferedReader br = readProcess(process);
				
				// Abre uma janela modal
				while((print = br.readLine()) != null){
					View.showMessage(print);
				}
			}else if(command.startsWith("rm")){ 
				// Tenta executar o comando
				Process process = executeCommand(command, "Erro ao deletar o arquivo local.");
				
				// Lê saida do comando
				BufferedReader br = readProcess(process);
				print = br.readLine();
				
				View.showMessage("Arquivo deletado com sucesso.");
				Cliente.runLocalCommand("ls =p");
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
			outputStream.flush();
			
			// Determina o que fazer na View baseado no que foi enviado ao Servidor
			if (Cliente.command.equalsIgnoreCase("ls -p")) {
				// Recebe o que foi processado
				BufferedReader br = readStream();
				
				output = br.readLine();
				print = output.split("@#");
				View.ServerFileList.clear();
				for(int i=0; i<print.length; i++){
					// Imprime no campo
					if(!print[i].endsWith("/")){
						View.ServerFileList.addElement(print[i]);
					}
				}
				
			}else if(Cliente.command.startsWith("ls -ltr ")){
				// Recebe o que foi processado
				BufferedReader br = readStream();
				
				output = br.readLine();
				print = output.split("@#");
				// Abre uma janela modal
				for(int i=0; i<print.length; i++){
					View.showMessage(print[i]);
				}
				
			}else if(Cliente.command.startsWith("receiveFileFromServer")){
				// Recebe o que foi processado
				String[] fileName = Cliente.command.split("@#");
				
				// Array buffer de bytes de escrita
				byte[] mybytearray = new byte[1024];
				
				// Lê o arquivo
				FileOutputStream fileOutputStream = new FileOutputStream(fileName[1]);
			    BufferedOutputStream byteOutputStream = new BufferedOutputStream(fileOutputStream);
			    int bytesRead = inputStream.read(mybytearray, 0, mybytearray.length);
			    
			    // Escreve o arquivo
			    byteOutputStream.write(mybytearray, 0, bytesRead);
			    byteOutputStream.close();
			    
			    // Mensagem ao usuário
			    View.showMessage("Download do arquivo " + fileName[1] + " concluído");
			    
			    //Atualiza a listagem de arquivos
			    Cliente.runLocalCommand("ls -p");
				
			}else if(Cliente.command.startsWith("sendFileToServer")){
				// Recebe o que foi processado
				String[] fileName = Cliente.command.split("@#");
				
				// Cria o nome do arquivo
				File file = new File(fileName[1]);
				
				// Array buffer de bytes de escrita
				byte[] byteArray = null;
			    
				try {
					byteArray = new byte[(int) file.length()];
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
					bis.read(byteArray, 0, byteArray.length);
					
				} catch (FileNotFoundException e) {
					System.err.println("Arquivo " + fileName[1] + " não encontrado");
				} catch (IOException e) {
					System.err.println("Não foi possível ler o arquivo " + fileName[1]);
				}
				
				// Envia o comando que foi clicado
				outputStream.write(byteArray,  0, (int) file.length());
				outputStream.flush();
				
			}else if(Cliente.command.startsWith("rm")){
				// Recebe o que foi processado
				BufferedReader br = readStream();
				
				output = br.readLine();
				View.showMessage("Arquivo removido do Servidor");
			}
			
		} catch (IOException e) {
			View.showMessage("Não foi possível obter conexão com o servidor");
		} catch (NullPointerException e){}
		
	}

	public static void execute(String command) {
		// Configura o comando e inicia uma nova Thread
		setCommand(command);
		new Thread(new Cliente()).run();
	}

}