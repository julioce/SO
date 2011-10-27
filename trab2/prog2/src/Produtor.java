import java.util.concurrent.Semaphore;


public class Produtor extends Thread {
	String Nome;
	int id;
	private Estoque estoque;
	private Semaphore semaforo;
	
	public Produtor(Estoque estoque, String nome, int id, Semaphore semaforo) {
		this.estoque = estoque;
		this.Nome = nome;
		this.id = id;
		this.semaforo = semaforo;
	}
	
	public String getNome() {
		return this.Nome;
	}
	
	public long getId() {
		return this.id;
	}
	
	public Estoque getEstoque() {
		return this.estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	
	@SuppressWarnings("unchecked")
	public void produzir() {
		synchronized (estoque) {
			/* Adiciona um recurso */
			int recursos = Main.getRecursosProduzidos();
			Recurso recurso = new Recurso((int)(recursos+1));
			this.estoque.getConteudo().add(recurso);
			Main.setRecursosProduzidos(recursos+1);

			View.changeTextProdutor(this.getId(), "Ativa");
			View.addTextOcorrencias("+ " + this.getNome() + "\t -> Recurso produzido: " + recurso + "\tTotal já produzido: " + (recursos+1) + "\tRecursos disponíveis no momento: " + estoque.getConteudo().size());
			System.out.println("+ " + this.getNome() + "\t -> Recurso produzido: " + recurso + "\tTotal já produzido: " + (recursos+1) + "\tRecursos disponíveis no momento: " + estoque.getConteudo().size());
			
			/* Notifica os consumidores que o estoque já foi reposto */
			estoque.notifyAll();
		}
	}

	public void run() {

		while (true) {
			
			/* Tenta entrar na região crítica */
			try {
				/* Decrementa o semáforo bloqueando as outras threads */
				/* Inicio da região crítica */
				this.semaforo.acquire();
				
				/* Verifica se ainda deve ser produzido algum recurso */
				if(estoque.getConteudo().size() < Configuracoes.MAX_RESOURCE_VALUE && Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
					this.produzir();
				}else{
					View.changeTextProdutor(this.getId(), "Terminou");
				}
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
			finally {
				/* Incrementa o semáforo liberando outras threads para produzirem */
				/* Fim da região crítica */
				this.semaforo.release();
			}
		
		
			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_PRODUCE));
			}
			catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
		
		
		
	}
	
}
