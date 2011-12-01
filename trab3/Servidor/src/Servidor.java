import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Servidor {

	int portNumber;
	String host;
	static int numberOfClients = 5;
	static List<String> localFiles = new LinkedList<String>();
	static List<Integer> localFilesStatus = new LinkedList<Integer>();

	// Limite de 5 clientes
	static Atendente t[] = new Atendente[numberOfClients];

	public Servidor() {
		// Cria o vetor de acordo com o limite de clientes servidos
		for (int i = 0; i < numberOfClients; i++) {
			t[i] = null;
		}
	}

	// Configura o host
	public void setHost(String host) {
		this.host = host;
	}

	// Configura a port
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	// Configura os arquivos correntes
	public static void setFiles() {
		Process process = null;
		String file = null;
		
		// Tenta executar o comando
		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec("ls -p");
		} catch (Exception e) {
			System.out.println("Erro ao executar pegar os arquivos locais.");
		}
		
		// Tenta ler a saida do comando
		try {
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			Servidor.localFiles.clear();
			while((file = br.readLine()) != null){
				// Lê somente os arquivos
				if(!file.endsWith("/")){
					Servidor.localFiles.add(file);
				}
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao obter saida do comando de listar arquivos locais.");
		}
	}
	
	// Seta os valores de acesso ao arquivo
	public static void setFilesStatus(){
		Servidor.localFilesStatus.clear();
		
		for(int i=0; i<localFiles.size(); i++){
			Servidor.localFilesStatus.add(1);
		}
	}
	
	public static void acquire(String fileName){
		Servidor.localFilesStatus.set(Servidor.localFiles.indexOf(fileName), 0);
	}
	
	public static void release(String fileName){
		Servidor.localFilesStatus.set(Servidor.localFiles.indexOf(fileName), 1);
	}
	
	public static int getSemaphoreStatus(String fileName){
		return Servidor.localFilesStatus.get(Servidor.localFiles.indexOf(fileName));
	}

	public void startServidor() {

		// Inicializa do servidor na porta. Deve ser > 1023 se nao for root
		try {
			
			System.out.println("Servidor criado em " + host);
			System.out.println("Foram criadas " + numberOfClients + " Threads Atendentes para " + numberOfClients + " Clientes");
			
			// Input e output para este socket serao criados na thread do cliente
			while(true){
				
				try {
					// Configura a listagem de arquivos para o semáforo
					setFiles();
					setFilesStatus();

					// Cria as threads de Atendentes com o limite de clientes servidos
					for (int i = 0; i < numberOfClients; i++) {
						
						if (t[i] == null) {
							t[i] = new Atendente((portNumber+i+1), t);
							t[i].start();
							System.out.println("Um novo Cliente se conectou a Thread Atendente " + (i+1) + " pela porta " + (portNumber+i+1));
							break;
						}
					}
					
				} catch (Exception e) {
					System.err.println("Erro ao iniciar a Thread Atendente");
				}
			}
			
			
		} catch (Exception e) {
			System.err.println("Erro ao criar o Servidor em " + host);
		}
		
	}

}