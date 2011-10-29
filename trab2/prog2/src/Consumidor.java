import java.util.concurrent.Semaphore;

public class Consumidor extends Thread {
	private String Nome;
	private int id;
	private Estoque estoque;
	private Semaphore semaforo;
	private int recursosConsumidos;
	
	public Consumidor(Estoque estoque, String nome, int id, Semaphore semaforo) {
		this.estoque = estoque;
		this.Nome = nome;
		this.id = id;
		this.semaforo = semaforo;
		this.recursosConsumidos = 0;
	}
	
	public Estoque getEstoque() {  
		return this.estoque;  
	}
	
	public void setEstoque(Estoque estoque) {  
		this.estoque = estoque;  
	}

	public String getNome() {
		return this.Nome;
	}
	
	public long getId() {
		return this.id;
	}
	
	private void addRecursosConsumidos(){
		this.recursosConsumidos = this.getRecursosConsumidos()+1;
	}
	
	private int getRecursosConsumidos(){
		return this.recursosConsumidos;
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void consumir() {
		synchronized (estoque) {

			/* Verifica se existem recursos no estoque */
			if (estoque.getConteudo().size() > 0) {
				
				/* Remove o primeiro recurso do buffer */
				Recurso recurso = (Recurso)estoque.getConteudo().remove(0);
				this.addRecursosConsumidos();
				
				/* Verifica qual versão de operação foi selecionada */
				if(!View.checkBoxVersaoB.isSelected()){
					/* Passa o Consumidor atual para o último e anda com os demais*/
					Main.fila.add(this);
					Main.fila.remove(0);
				}
				
				View.changeStatusConsumidor(this.getId(), "Ativa", Configuracoes.ACTIVE_COLOR);
				View.changeItensConsumidor(this.getId(), this.getRecursosConsumidos());
				View.addTextOcorrencias("- " + this.getNome() + "\t-> Recurso consumido: " + recurso + "\t\tRecursos disponíveis no momento: " + estoque.getConteudo().size());
				
				/* Faz o consumo não ser tão rápido */
				try {
					Thread.sleep((int)(Configuracoes.MAX_TIME_TO_CONSUME));
				}
				catch (InterruptedException e) { e.printStackTrace(); }
				
			}
			else {
				
				/* Não existe recursos no estoque */
				try {
					if(Main.getRecursosProduzidos() != Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
						/* Espera o produtor notificar que houve uma reposição no estoque */
						View.changeStatusConsumidor(getId(), "Waiting", Configuracoes.WAITING_COLOR);
						View.addTextOcorrencias("! " + this.getNome() +  " -> Esperando recurso ser produzido...");
						estoque.wait();
					}
					else{
						/* Não serão mais produzidos recursos, então a thread morre */
						View.changeStatusConsumidor(getId(), "Terminou", Configuracoes.TERMINATED_COLOR);
						View.addTextOcorrencias("X " + this.getNome() +  "\t-> Simulação termina porque não serão mais produzidos recursos");
						this.stop();
					}
					
				}
				catch (InterruptedException e) { e.printStackTrace(); }
				
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		
		while (true) {
			/* Tenta entrar na região crítica */
			try {
				/* Decrementa o semáforo bloqueando as outras threads */
				/* Inicio da região crítica */
				View.setSemaforoConsumidor(semaforo.toString());
				this.semaforo.acquire();
				
				/* Verifica qual versão foi selecionada */
				if(!View.checkBoxVersaoB.isSelected()){
					/* Verfica de a thread atual é a primeira da fila */
					if(Main.fila.get(0).equals(this)){
						this.consumir();
					}
				}else{
					/* Consumo sem obedecer a fila */
					this.consumir();
				}
				
			} catch (InterruptedException e) { e.printStackTrace(); }
			finally {
				/* Incrementa o semáforo bloqueando as outras threads */
				/* Fim da região crítica */
				this.semaforo.release();
				View.setSemaforoConsumidor(semaforo.toString());
			}
			
			try {
				View.changeStatusConsumidor(getId(), "Sleeping", Configuracoes.SLEEPING_COLOR);
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_CONSUME));
			}
			catch (InterruptedException e) { e.printStackTrace(); }
			
			/* Verifica se o Consumidor já terminou */
			if(estoque.getConteudo().size() == 0 && Main.getRecursosProduzidos() == Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
				this.stop();
				View.changeButtonIniciar("Concluído");
			}
		}
	}
   
}
