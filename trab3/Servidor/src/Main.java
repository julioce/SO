public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Cria o servidor
		Servidor servidor = new Servidor();

		// Configura os parâmetros
		servidor.setHost("localhost");
		servidor.setPortNumber(2222);

		// Inicia de fato o cliente
		servidor.startServidor();
	}

}
