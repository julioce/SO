import java.util.concurrent.Semaphore;

public class Produtor extends Thread {
	String Nome;
	int id;
	private Estoque estoque;
	private Semaphore semaforo;
	private int recursosProduzidos;
	
	public Produtor(Estoque estoque, String nome, int id, Semaphore semaforo) {
		this.estoque = estoque;
		this.Nome = nome;
		this.id = id;
		this.semaforo = semaforo;
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
	
	@Override
	public long getId() {
		return this.id;
	}

	private void addRecursosProduzidos(){
		this.recursosProduzidos = this.getRecursosProduzidos()+1;
	}
	
	private int getRecursosProduzidos(){
		return this.recursosProduzidos;
	}
	
	@SuppressWarnings("unchecked")
	public void produzir() {
		synchronized (estoque) {
			/* Adiciona um recurso e o contabiliza */
			int recursos = Main.getRecursosProduzidos();
			Recurso recurso = new Recurso((recursos+1));
			this.estoque.getConteudo().add(recurso);
			this.addRecursosProduzidos();
			Main.setRecursosProduzidos(recursos+1);
			
			View.changeItensProdutor(this.getId(), this.getRecursosProduzidos());
			View.addTextOcorrencias("+ " + this.getNome() + "\t\t-> Recurso produzido: " + recurso + "\tTotal já produzido: " + (recursos+1) + "\tRecursos disponíveis no momento: " + estoque.getConteudo().size());

			/* Faz a produção não ser tão rápida assim */
			try { Thread.sleep((Configuracoes.MAX_TIME_TO_PRODUCE)); }
			catch (InterruptedException e) { e.printStackTrace(); }
			
			/* Notifica os consumidores que o estoque já foi reposto */
			estoque.notifyAll();
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void run() {
		View.iniciar.setText("Executando...");
		View.iniciar.setEnabled(false);
		View.checkBoxVersaoB.setEnabled(false);

		while (true) {
			
			/* Tenta entrar na região crítica */
			try {
				/* Decrementa o semáforo bloqueando as outras threads */
				/* Inicio da região crítica */
				View.setSemaforoProdutor(semaforo.toString());
				this.semaforo.acquire();
				
				/* Verifica se ainda deve ser produzido algum recurso */
				if(estoque.getConteudo().size() < Configuracoes.MAX_RESOURCE_VALUE && Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
					View.changeStatusProdutor(this.getId(), "Ativa", Configuracoes.ACTIVE_COLOR);
					this.produzir();
				}
			} catch (InterruptedException e) { e.printStackTrace(); }
			finally {
				/* Incrementa o semáforo liberando outras threads para produzirem */
				/* Fim da região crítica */
				this.semaforo.release();
				View.setSemaforoProdutor(semaforo.toString());
			}
			

			View.changeStatusProdutor(this.getId(), "Sleeping", Configuracoes.SLEEPING_COLOR);
			
			try {
				/* Se o Produtor já terminou */
				if(Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
					Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_PRODUCE));
				}
				else{
					View.changeStatusProdutor(this.getId(), "Terminou", Configuracoes.TERMINATED_COLOR);
					this.stop();
				}
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		
	}
	
}
