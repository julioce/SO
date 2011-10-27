
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
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void consumir() {
		synchronized (estoque) {

			/* Verifica se existem recursos no estoque */
			if (estoque.getConteudo().size() > 0) {
				/* Remove o primeiro recurso do buffer */
				Recurso recurso = (Recurso)estoque.getConteudo().remove(0);
				
				/* Passa o Consumidor atual para o último e anda com os demais*/
				Main.fila.add(this);
				Main.fila.remove(0);
				
				View.changeTextConsumidor(getId(), "Consumindo");
				try {
					Thread.sleep((int)(Configuracoes.MAX_TIME_TO_CONSUME));
				}
				catch (InterruptedException e) { e.printStackTrace(); }
				View.changeTextConsumidor(getId(), "Aguardando");
				View.addTextOcorrencias("- " + this.getNome() + "-> Recurso consumido: " + recurso);
				System.out.println("- " + this.getNome() + "-> Recurso consumido: " + recurso);
				
			}
			else {
				/* Não existe recursos no estoque */
				try {
					if(Main.getRecursosProduzidos() != Configuracoes.TOTAL_RECURSOS_A_SER_PRODUZIDO){
						/* Espera o produtor notificar que houve uma reposição no estoque */
						View.changeTextConsumidor(getId(), "Aguardando");
						View.addTextOcorrencias("! " + this.getNome() +  " -> Esperando recurso ser produzido...");
						System.out.println("! " + this.getNome() +  " -> Esperando recurso ser produzido...");
						estoque.wait();
					}
					else{
						/* Não serão mais produzidos recursos, então a thread morre */
						View.changeTextConsumidor(getId(), "Terminou");
						View.addTextOcorrencias("X " + this.getNome() +  "-> Simulação termina porque não serão mais produzidos recursos");
						System.out.println("X " + this.getNome() +  "-> Simulação termina porque não serão mais produzidos recursos");
						this.stop();
					}
					
				}
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}
	
	public void run() {
		
		while (true) {
			View.changeTextConsumidor(getId(), "Aguardando");
			
			/* Verfica de a thread atual é a primeira da fila */
			if(Main.fila.get(0).equals(this)){
				this.consumir();
			}

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
