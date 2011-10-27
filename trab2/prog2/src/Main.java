import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class Main {
	/* Inicia a contagem de produtos */
	public static Integer recursosProduzidos = 0;
	
	/* Cria os semáforos de sincronização */
	public static Semaphore semaforoProdutor = new Semaphore (1);
	public static Semaphore semaforoConsumidor = new Semaphore (1);
	
	/* Fila de consumidores */  
	@SuppressWarnings("rawtypes")
	public static List fila = new LinkedList();
	
	public static int getRecursosProduzidos(){
		return recursosProduzidos;
	}
	
	public static void setRecursosProduzidos(Integer recursosProduzidos) {
		Main.recursosProduzidos = recursosProduzidos;
	}
	
	@SuppressWarnings("unchecked")
	public static void inicializaEntidades(){
		/* Repositório de recursos */  
		Estoque estoque = new Estoque();
		
		/* Inicializa os Produtores */
		Produtor produtor1 = new Produtor(estoque, "Produtor 1", 1, semaforoProdutor);
		Produtor produtor2 = new Produtor(estoque, "Produtor 2", 2, semaforoProdutor);
		Produtor produtor3 = new Produtor(estoque, "Produtor 3", 3, semaforoProdutor);
		
		/* Inicializa os Consumidores e os adiciona na fila */
		Consumidor consumidor1 = new Consumidor(estoque, "Consumidor 1", 1, semaforoConsumidor);
		Consumidor consumidor2 = new Consumidor(estoque, "Consumidor 2", 2, semaforoConsumidor);
		Consumidor consumidor3 = new Consumidor(estoque, "Consumidor 3", 3, semaforoConsumidor);
		Consumidor consumidor4 = new Consumidor(estoque, "Consumidor 4", 4, semaforoConsumidor);
		Consumidor consumidor5 = new Consumidor(estoque, "Consumidor 5", 5, semaforoConsumidor);
		fila.add(consumidor1);
		fila.add(consumidor2);
		fila.add(consumidor3);
		fila.add(consumidor4);
		fila.add(consumidor5);
		
		if(View.checkBoxVersaoB.isEnabled()){
			/* Consumidor tem menor prioridade que o Produtor */
			produtor1.setPriority(Thread.NORM_PRIORITY);
			produtor2.setPriority(Thread.NORM_PRIORITY);
			produtor3.setPriority(Thread.NORM_PRIORITY);
			consumidor1.setPriority(Thread.MIN_PRIORITY);
			consumidor2.setPriority(Thread.MIN_PRIORITY);
			consumidor3.setPriority(Thread.MIN_PRIORITY);
			consumidor4.setPriority(Thread.MIN_PRIORITY);
			consumidor5.setPriority(Thread.MIN_PRIORITY);
		}
		
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
		/* Inicializa as entidades */
		Main main = new Main();
		
		/* Inicializa a GUI */
		View view = new View();
	}

}
