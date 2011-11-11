
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Cria o cliente
		Cliente cliente = new Cliente();
		
		//Configura os parâmetros
		cliente.setHost("localhost");
		cliente.setPortNumber(2222);
		
		//Inicia de fato o cliente
		cliente.startCliente();
	}

}
