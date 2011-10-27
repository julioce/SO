
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
			/* Insere um recurso no estoque */
			Recurso recurso = new Recurso((int)(Main.getRecursosProduzidos()+1));
			this.estoque.getConteudo().add(recurso);

			View.changeTextProdutor(this.getId(), "Produzindo");
			try {
				Thread.sleep((int)(Configuracoes.MAX_TIME_TO_PRODUCE));
			}
			catch (InterruptedException e) { e.printStackTrace(); }
			View.addTextOcorrencias("+ " + this.getNome() + "\t -> Recurso produzido: " + recurso + "\tTotal já produzido: " + (Main.getRecursosProduzidos()+1) + "\tRecursos disponíveis no momento: " + estoque.getConteudo().size());
			System.out.println("+ " + this.getNome() + "\t -> Recurso produzido: " + recurso + "\tTotal já produzido: " + (Main.getRecursosProduzidos()+1) + "\tRecursos disponíveis no momento: " + estoque.getConteudo().size());
			
			/* Notifica os consumidores que o estoque já foi reposto */

			View.changeTextProdutor(this.getId(), "Aguardando");
			estoque.notifyAll();
			Main.setRecursosProduzidos(Main.getRecursosProduzidos()+1);
		}
	}

	public void run() {

		while (Main.getRecursosProduzidos() < Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO) {
			
			View.changeTextProdutor(this.getId(), "Aguardando...");
			
			if(estoque.getConteudo().size() < Configuracoes.MAX_RESOURCE_VALUE){
				this.produzir();
			}
			
			View.changeTextProdutor(this.getId(), "Aguardando");
			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_PRODUCE));
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		View.changeTextProdutor(this.getId(), "Terminou");
		
	}
	
}
