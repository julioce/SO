
public class Consumidor extends Thread {
	String Nome;
	int id;
	private Estoque estoque;
	
	public Consumidor(Estoque estoque, String nome, int id) {
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

	@SuppressWarnings("deprecation")
	public void consumir() {
		synchronized (estoque) {

			/* Verifica se existem recursos no estoque */
			if (estoque.getConteudo().size() > 0) {
				View.changeTextConsumidor(getId(), "Consumindo...");
				/* Remove o primeiro da fila */
				Recurso recurso = (Recurso)estoque.getConteudo().remove(0);
				System.out.println("- " + this.getNome() + "\t -> Recurso consumido: " + recurso);
				
			}
			else {
				/* Não existe recursos no estoque */
				try {
					if(Main.getRecursosProduzidos() != Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
						/* Espera o produtor notificar que houve uma reposição no estoque */
						View.changeTextConsumidor(getId(), "Aguardando...");
						System.out.println("! " + this.getNome() +  "\t -> Esperando recurso ser reposto...");
						estoque.wait();
					}
					else{
						/* Não serão mais produzidos recursos, então a thread morre */
						View.changeTextConsumidor(getId(), "Saindo...");
						System.out.println("X " + this.getNome() +  "\t -> Sai do sistema porque não serão mais produzidos recursos");
						this.stop();
						View.iniciar.setText("Concluído");
					}
					
				}
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}
	
	public void run() {
		
		while (true) {
			
			this.consumir();
			
			try {
				Thread.sleep((int)(Math.random() * Configuracoes.MAX_TIME_TO_CONSUME));
			}
			catch (InterruptedException e) { e.printStackTrace(); }
			
			/* Verifica se o Consumidor já terminou */
			if(estoque.getConteudo().size() == 0 && Main.getRecursosProduzidos() == Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
				View.changeButtonIniciar("Concluído");
			}
		}
	}
   
}
