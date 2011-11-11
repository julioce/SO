import java.io.*;
import java.net.*;

public class Servidor {

	int portNumber;
	String host;
	static int numbersOfClients = 5;
	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;

	// Limite de 5 clientes
	static Cliente t[] = new Cliente[numbersOfClients];

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

		// Inicializa do servidor na porta - deve ser > 1023 se não for root
		try {
			serverSocket = new ServerSocket(this.portNumber);
			System.out.println("Servidor criado em " + this.portNumber);
		} catch (IOException e) {
			System.err.println("Erro ao criar o Servidor " + e);
		}

		// Cria o objeto do tipo ServerSocket para ouvir e aceitar conexões e
		// Input e output
		// Input e output para este socket serão criados na thread do cliente
		while (true) {
			try {
				clientSocket = serverSocket.accept();

				// Cria as threads de acordo com o limite de clientes servidos
				for (int i = 0; i < numbersOfClients; i++) {
					if (t[i] == null) {
						(t[i] = new Cliente(clientSocket, t)).start();
						break;
					}
				}
			} catch (IOException e) {
				System.err.println("Erro ao criar Clientes para o Servidor " + e);
			}
		}
	}

}