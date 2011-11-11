import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

class Cliente extends Thread {

	static int numbersOfClients = 5;
	DataInputStream is = null;
	PrintStream os = null;
	Socket clientSocket = null;
	Cliente t[];

	public Cliente(Socket clientSocket, Cliente[] t) {
		this.clientSocket = clientSocket;
		this.t = t;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String line;
		String name;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			os.println("Digite o seu nome:");
			name = is.readLine();
			os.println("Bemvindo " + name + " ao chat.\nPara deixar o chat digite /quit como mensagem");

			// Mensagem de boas vindas ao novo usuário
			for (int i = 0; i < numbersOfClients; i++) {
				if (t[i] != null && t[i] != this) {
					t[i].os.println("*** Novo usuário " + name + " entrou no chat! ***");
				}
			}

			while (true) {
				// Faz a leitura do console
				line = is.readLine();
				
				// Sai do loop se digitado palavra chave
				if (line.startsWith("/quit")) {
					break;
				}

				// Imprime a mensagem
				for (int i = 0; i < numbersOfClients; i++) {
					if (t[i] != null) {
						t[i].os.println("<" + name + "> " + line);
					}
				}
			}

			for (int i = 0; i < numbersOfClients; i++) {
				if (t[i] != null && t[i] != this) {
					t[i].os.println("*** O usuário " + name + " deixou o chat! ***");
				}
			}

			os.println("*** Bye " + name + " ***");

			// Limpeza do slot do servidor liberando para outro cliente se conectar
			for (int i = 0; i < numbersOfClients; i++) {
				if (t[i] == this) {
					t[i] = null;
				}
			}

			// Fecha o input, output e o socket
			is.close();
			os.close();
			clientSocket.close();

		} catch (IOException e) {
			System.err.println("Erro ao iniciar a Thread do servidor para o Cliente " + e);
		}
	}
}