import java.io.*;
import java.net.*;

public class Servidor {

	int portNumber;
	String host;
	static int numberOfClients = 5;
	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	// Limite de 5 clientes
	static Cliente t[] = new Cliente[numberOfClients];

	public Servidor() {
		// Construtor
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public void startServidor() {

		// Inicializa do servidor na porta - deve ser > 1023 se nao for root
		try {
			serverSocket = new ServerSocket(this.portNumber);
			System.out.println("Servidor criado em " + this.host + " na porta " + this.portNumber);
			System.out.println("Foram criadas " + numberOfClients + " instâncias de threads para atender " + numberOfClients + " clientes");
		} catch (IOException e) {
			System.err.println("Erro ao criar o Servidor " + this.host + " na porta " + this.portNumber + "\nErro" + e);
		}

		// Cria o objeto do tipo ServerSocket para ouvir e aceitar conexoes e
		// Input e output
		// Input e output para este socket serao criados na thread do cliente
		while(true){
			try {
				clientSocket = serverSocket.accept();

				// Cria as threads de acordo com o limite de clientes servidos
				for (int i = 0; i < numberOfClients; i++) {
					if (t[i] == null) {
						(t[i] = new Cliente(clientSocket, t)).start();
						break;
					}
				}
				System.out.println("Um novo cliente se conectou em " + clientSocket.getLocalAddress());
				
			} catch (IOException e) {
				System.err.println("Erro ao criar Clientes para o Servidor " + e);
			}
		}
	}

}