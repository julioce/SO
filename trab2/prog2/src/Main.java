
public class Main {
	/* Inicia a contagem de produtos */
	private static Integer recursosProduzidos = 0;
	
	public static int getRecursosProduzidos(){
		return recursosProduzidos;
	}
	
	public static void setRecursosProduzidos(Integer recursosProduzidos) {
		Main.recursosProduzidos = recursosProduzidos;
	}
	
	public static void inicializaEntidades(){
		/* Repositório de recursos */  
		Estoque estoque = new Estoque();
		
		/* Inicializa os Produtores */
		Produtor produtor1 = new Produtor(estoque);
		produtor1.setNome("Produtor 1");
		Produtor produtor2 = new Produtor(estoque);
		produtor2.setNome("Produtor 2");
		Produtor produtor3 = new Produtor(estoque);
		produtor3.setNome("Produtor 3");
		
		/* Inicializa os Consumidores */
		Consumidor consumidor1 = new Consumidor(estoque);
		consumidor1.setNome("Consumidor 1");
		Consumidor consumidor2 = new Consumidor(estoque);
		consumidor2.setNome("Consumidor 2");
		Consumidor consumidor3 = new Consumidor(estoque);
		consumidor3.setNome("Consumidor 3");
		Consumidor consumidor4 = new Consumidor(estoque);
		consumidor4.setNome("Consumidor 4");
		Consumidor consumidor5 = new Consumidor(estoque);
		consumidor5.setNome("Consumidor 5");
		
		consumidor1.setPriority(Thread.MIN_PRIORITY);
		consumidor2.setPriority(Thread.MIN_PRIORITY);
		consumidor3.setPriority(Thread.MIN_PRIORITY);
		consumidor4.setPriority(Thread.MIN_PRIORITY);
		consumidor5.setPriority(Thread.MIN_PRIORITY);
		
		/* Inicia as threads */
		produtor1.start();
		produtor2.start();
		produtor3.start();
		consumidor1.start();
		consumidor2.start();
		consumidor3.start();
		consumidor4.start();
		consumidor5.start();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		/* Inicializa as estruturas */
		Main main = new Main();
		
		/* Inicializa a GUI */
		View view = new View();
		
	}

}
