
public class Main {
	
	public static void main(String[] args) {
		/* Inicializa a GUI */
		View view = new View();
		view.startView();
	}

	public static void createClient(String host) {
		if(!View.hostField.getText().equals("")){
			// Cria o cliente
			Cliente cliente = new Cliente();
			
			// Configura os parâmetros
			cliente.setHost(host);
			cliente.setPortNumber(2222);
			
			// Inicia de fato o cliente
			cliente.startCliente();
		}
	}

}
