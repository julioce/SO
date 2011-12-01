public class Main {

	public static void main(String[] args) {
		// Cria o servidor
		Servidor servidor = new Servidor();

		// Configura os parametros
		servidor.setHost("localhost");
		servidor.setPortNumber(2220);

		// Inicia de fato o Servidor
		servidor.startServidor();
	}

}
