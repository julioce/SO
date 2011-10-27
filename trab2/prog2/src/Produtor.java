
public class Produtor extends Thread {
	String Nome;
	int id;
	private Estoque estoque;
	
	public Produtor(Estoque estoque, String nome, int id) {
		this.estoque = estoque;
		this.Nome = nome;
		this.id = id;
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

		while (Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO) {
			
			if(estoque.getConteudo().size() < Configuracoes.MAX_RESOURCE_VALUE && Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
				Main.semaforo.acquireUninterruptibly();
				this.produzir();
				Main.semaforo.release();
			}
			
			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_PRODUCE));
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		View.changeTextProdutor(this.getId(), "Terminou");
		
	}
	
}
